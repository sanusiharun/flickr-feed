package com.flickrfeed.flickrfeed.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.flickrfeed.flickrfeed.model.entity.FlickrFeed;
import com.flickrfeed.flickrfeed.model.request.GetAvailableFlickrFeedRequest;
import com.flickrfeed.flickrfeed.model.response.JsonFlickrFeedResponse;
import com.flickrfeed.flickrfeed.service.GetAllPublicPhotosService;
import com.flickrfeed.flickrfeed.service.GrabPublicPhotosService;

@RestController
@RequestMapping("feed/v1")
public class FeedController {
	
	GetAllPublicPhotosService getAllPublicPhotosService;
	GrabPublicPhotosService grabPublicPhotosService;

	public FeedController(GetAllPublicPhotosService getAllPublicPhotosService, GrabPublicPhotosService grabPublicPhotosService) {
		this.getAllPublicPhotosService = getAllPublicPhotosService;
		this.grabPublicPhotosService = grabPublicPhotosService;
	}

	@GetMapping(value = "get-flickr-feed", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JsonFlickrFeedResponse getAvailableFlickrFeed(GetAvailableFlickrFeedRequest request) throws JsonMappingException, JsonProcessingException {
		
		return getAllPublicPhotosService.getAllPublicPhotos();
	}
	
	@PostMapping(value="grab-flickr-feed", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public FlickrFeed grabFlickrFeed() throws JsonMappingException, JsonProcessingException {
		FlickrFeed flickrFeed = grabPublicPhotosService.save();
		return flickrFeed;
		
	}
	
}
