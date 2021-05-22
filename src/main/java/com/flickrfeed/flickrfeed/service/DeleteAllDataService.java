package com.flickrfeed.flickrfeed.service;

import javax.transaction.Transactional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flickrfeed.flickrfeed.model.response.DeleteAllDataResponse;
import com.flickrfeed.flickrfeed.model.response.JsonFlickrFeedResponse;
import com.flickrfeed.flickrfeed.repository.FlickrFeedRepository;
import com.flickrfeed.flickrfeed.repository.ItemsRepository;
import com.flickrfeed.flickrfeed.repository.MediaRepository;
import com.flickrfeed.flickrfeed.util.Constants;

@Service
public class DeleteAllDataService {

	private FlickrFeedRepository flickrFeedRepository;
	private ItemsRepository itemsRepository;
	private MediaRepository mediaRepository;

	public DeleteAllDataService(FlickrFeedRepository flickrFeedRepository, ItemsRepository itemsRepository,
			MediaRepository mediaRepository) {
		this.flickrFeedRepository = flickrFeedRepository;
		this.itemsRepository = itemsRepository;
		this.mediaRepository = mediaRepository;
	}

	@Transactional
	public DeleteAllDataResponse deleteAll() {
		mediaRepository.deleteAll();
		itemsRepository.deleteAll();
		flickrFeedRepository.deleteAll();
		return DeleteAllDataResponse.builder().status("Successfully clear all data").build();
	}
}
