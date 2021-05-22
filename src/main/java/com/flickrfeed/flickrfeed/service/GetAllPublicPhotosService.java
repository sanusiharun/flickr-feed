package com.flickrfeed.flickrfeed.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flickrfeed.flickrfeed.model.response.JsonFlickrFeedResponse;
import com.flickrfeed.flickrfeed.util.Constants;

@Service
public class GetAllPublicPhotosService {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ModelMapper modelMapper;
	
	public JsonFlickrFeedResponse getAllPublicPhotos() throws JsonMappingException, JsonProcessingException {
		ResponseEntity<String> resp = restTemplate.getForEntity(Constants.MAIN_URL, String.class);
		String response = null;
		if(!resp.getBody().startsWith("{"))
			response = resp.getBody().substring(resp.getBody().indexOf("{") , resp.getBody().lastIndexOf(")"));
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonFlickrFeedResponse jsonFlickrFeed = objectMapper.readValue(response, JsonFlickrFeedResponse.class);
		return jsonFlickrFeed;
	}

}
