package com.flickrfeed.flickrfeed.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.flickrfeed.flickrfeed.request.GetAvailableFlickrFeedRequest;
import com.flickrfeed.flickrfeed.response.JsonFlickrFeedResponse;
import com.flickrfeed.flickrfeed.service.GetAllPublicPhotosService;

@RestController
@RequestMapping("feed/v1")
public class FeedController {
	
	GetAllPublicPhotosService getAllPublicPhotosService;

	public FeedController(GetAllPublicPhotosService getAllPublicPhotosService) {
		this.getAllPublicPhotosService = getAllPublicPhotosService;
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JsonFlickrFeedResponse getAvailableFlickrFeed(GetAvailableFlickrFeedRequest request) throws JsonMappingException, JsonProcessingException {
		
		return getAllPublicPhotosService.getAllPublicPhotos();
	}
	
}
