package com.flickrfeed.flickrfeed.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.flickrfeed.flickrfeed.model.entity.FlickrFeed;
import com.flickrfeed.flickrfeed.model.entity.Items;
import com.flickrfeed.flickrfeed.model.entity.Media;
import com.flickrfeed.flickrfeed.model.response.ItemsResponse;
import com.flickrfeed.flickrfeed.model.response.JsonFlickrFeedResponse;
import com.flickrfeed.flickrfeed.repository.FlickrFeedRepository;
import com.flickrfeed.flickrfeed.repository.ItemsRepository;
import com.flickrfeed.flickrfeed.repository.MediaRepository;

@Service
public class GrabPublicPhotosService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ModelMapper modelMapper;

	private GetAllPublicPhotosService getAllPublicPhotosService;
	private FlickrFeedRepository flickrFeedRepository;
	private ItemsRepository itemsRepository;
	private MediaRepository mediaRepository;

	public GrabPublicPhotosService(GetAllPublicPhotosService getAllPublicPhotosService,
			FlickrFeedRepository flickrFeedRepository, ItemsRepository itemsRepository,
			MediaRepository mediaRepository) {
		this.getAllPublicPhotosService = getAllPublicPhotosService;
		this.flickrFeedRepository = flickrFeedRepository;
		this.itemsRepository = itemsRepository;
		this.mediaRepository = mediaRepository;
	}
	@Transactional
	public FlickrFeed save() throws JsonMappingException, JsonProcessingException {
		JsonFlickrFeedResponse jsonFlickrFeedResponse = getAllPublicPhotosService.getAllPublicPhotos();
		FlickrFeed flickrFeed = convertToFlickrFeed(jsonFlickrFeedResponse);
		FlickrFeed exists = isExistsFeed(flickrFeed.getTitle());
		
		if (null == exists) {
			flickrFeed = flickrFeedRepository.save(flickrFeed);
		} else {
			exists.setDescription(flickrFeed.getDescription());
			exists.setModified(flickrFeed.getModified());
			exists.setGenerator(flickrFeed.getGenerator());
			exists.setLink(flickrFeed.getLink());
			exists.setDescription(flickrFeed.getDescription());
			
			flickrFeed = flickrFeedRepository.save(exists);
		}
		
		if(jsonFlickrFeedResponse.getItems().size() > 0) {
			List<ItemsResponse> itemsReponse = jsonFlickrFeedResponse.getItems();
			List<Items> items = itemsReponse.stream().map(this::convertToItems).collect(Collectors.toList());
			for(Items i : items) {
				Items ei = isExistsItems(i.getTitle());
				if (null == ei) {
					i.setFlickrFeedId(flickrFeed.getId());
					
					i = itemsRepository.save(i);
				} else {
					ei.setLink(i.getLink());
					ei.setAuthor(ei.getAuthor());
					ei.setAuthorId(i.getAuthorId());
					ei.setDateTaken(i.getDateTaken());
					ei.setDescription(i.getDescription());
					ei.setPublished(i.getPublished());
					ei.setTags(i.getTags());
					ei.setFlickrFeedId(flickrFeed.getId());
					i = itemsRepository.save(ei);
				}
				if(i.getMedia() != null) {
					
					Media m = isExistsMedia(i.getMedia().getM());
					if(null == m) {
						m = Media.builder().m(i.getMedia().getM()).itemsId(i.getId()).build();
						m.setItemsId(i.getId());
						mediaRepository.save(m);
					}
				}
			}
		}
		 
		

		return flickrFeed;
	}

	private FlickrFeed isExistsFeed(String title) {
		Optional<FlickrFeed> f = flickrFeedRepository.findByTitle(title);
		if(f.isPresent())
			return f.get();
		return null;

	}

	private Items isExistsItems(String title) {
		Optional<Items> i = itemsRepository.findByTitle(title);
		if(i.isPresent())
			return i.get();
		return null;

	}
	
	private Media isExistsMedia(String m) {
		Optional<Media> me = mediaRepository.findByM(m);
		if(me.isPresent())
			return me.get();
		return null;

	}

	
	private FlickrFeed convertToFlickrFeed(JsonFlickrFeedResponse response) {
		return modelMapper.map(response, FlickrFeed.class);
	}
	
	private Items convertToItems(ItemsResponse response) {
		return modelMapper.map(response, Items.class);
	}


}
