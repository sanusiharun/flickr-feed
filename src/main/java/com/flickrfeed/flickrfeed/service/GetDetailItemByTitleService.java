package com.flickrfeed.flickrfeed.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flickrfeed.flickrfeed.model.entity.Items;
import com.flickrfeed.flickrfeed.model.response.ItemsResponse;
import com.flickrfeed.flickrfeed.repository.ItemsRepository;

@Service
public class GetDetailItemByTitleService {

	private ItemsRepository itemsRepository;
	
	@Autowired
	ModelMapper modelMapper;
	
	public GetDetailItemByTitleService(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}
	
	public ItemsResponse getDetailItemByTitle(String title) {
			Items item = itemsRepository.findByTitle(title).orElse(null);
		return convertToItems(item);
	}
	
	private ItemsResponse convertToItems(Items response) {
		return response == null ? null : modelMapper.map(response, ItemsResponse.class);
	}

}
