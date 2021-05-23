package com.flickrfeed.flickrfeed.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.flickrfeed.flickrfeed.model.entity.FlickrFeed;
import com.flickrfeed.flickrfeed.model.request.FilterFlickrFeedRequest;
import com.flickrfeed.flickrfeed.model.response.DeleteAllDataResponse;
import com.flickrfeed.flickrfeed.model.response.FilterFlickrFeedResponse;
import com.flickrfeed.flickrfeed.model.response.GetImageResponse;
import com.flickrfeed.flickrfeed.model.response.ItemsResponse;
import com.flickrfeed.flickrfeed.model.response.JsonFlickrFeedResponse;
import com.flickrfeed.flickrfeed.service.DeleteAllDataService;
import com.flickrfeed.flickrfeed.service.GetAllPublicPhotosService;
import com.flickrfeed.flickrfeed.service.GetDetailItemByIdService;
import com.flickrfeed.flickrfeed.service.GetImageService;
import com.flickrfeed.flickrfeed.service.GrabPublicPhotosService;
import com.flickrfeed.flickrfeed.service.SearchFlickrFeedService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("feed/v1")
public class FeedController {

	GetAllPublicPhotosService getAllPublicPhotosService;
	GrabPublicPhotosService grabPublicPhotosService;
	GetDetailItemByIdService getDetailItemByIdService;
	SearchFlickrFeedService searchFlickrFeedService;
	DeleteAllDataService deleteAllDataService;
	GetImageService getImageService;

	public FeedController(GetAllPublicPhotosService getAllPublicPhotosService,
			GrabPublicPhotosService grabPublicPhotosService, GetDetailItemByIdService getDetailItemByIdService,
			SearchFlickrFeedService searchFlickrFeedService, DeleteAllDataService deleteAllDataService,
			GetImageService getImageService) {
		this.getAllPublicPhotosService = getAllPublicPhotosService;
		this.grabPublicPhotosService = grabPublicPhotosService;
		this.getDetailItemByIdService = getDetailItemByIdService;
		this.searchFlickrFeedService = searchFlickrFeedService;
		this.deleteAllDataService = deleteAllDataService;
		this.getImageService = getImageService;

	}

	@Operation(summary = "Get Flicker Feed from Flickr source", description = "API Get available data from Flickr source")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Ups something error") })
	@GetMapping(value = "get-flickr-feed", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<JsonFlickrFeedResponse> getAvailableFlickrFeed()
			throws JsonMappingException, JsonProcessingException {

		JsonFlickrFeedResponse response = getAllPublicPhotosService.getAllPublicPhotos();
		return response == null ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Get Flicker Feed from App DB", description = "API Get by ItemId from App DB")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Ups something error") })
	@GetMapping(value = "get-flickr-feed/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ItemsResponse> getFlickrFeedByItemId(@PathVariable("itemId") Long itemId)
			throws JsonMappingException, JsonProcessingException {

		ItemsResponse response = getDetailItemByIdService.getDetailItemById(itemId);
		return response == null ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Get Flicker Feed from App DB", description = "Pagination with search criteria")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Ups something error") })
	@GetMapping(value = "get-flickr-feed/filter", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<FilterFlickrFeedResponse> searchFlickrFeed(FilterFlickrFeedRequest request) {

		FilterFlickrFeedResponse response = searchFlickrFeedService.search(request);
		return response == null ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Operation(summary = "Delete Flicker Feed from App DB", description = "Reset DB")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Ups something error") })
	@DeleteMapping
	public ResponseEntity<DeleteAllDataResponse> deleteAllData() {

		DeleteAllDataResponse response = deleteAllDataService.deleteAll();
		return response == null ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(response, HttpStatus.OK);
	}
	@Operation(summary = "Get Image Feed by image url", description ="Image from app folder")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = 500, message = "Ups something error") })
	@GetMapping(value = "get-item-image")
	public ResponseEntity<Resource> getImage(@RequestParam("link") String link, HttpServletRequest request) throws IOException {

		GetImageResponse image = getImageService.getImage(link);
		if(image.getFilename()== null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		Resource resource = image.getFile();		
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch(IOException e) {
			
		}
		if(contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
	    return ResponseEntity.ok()
	            .contentType(MediaType.parseMediaType(contentType))
	            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("%s; filename=%s", image.getFilename(),image.getResource()))
	            .body(resource);
	}

	@Operation(summary = "Migrate flickr feed from original source to app database", description = "API pull data from Flickr Feed")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created"),
			@ApiResponse(code = 500, message = "Ups something error") })
	@PostMapping(value = "grab-flickr-feed", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<FlickrFeed> grabFlickrFeed() throws JsonMappingException, JsonProcessingException {

		FlickrFeed response = grabPublicPhotosService.save();
		return response == null ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(response, HttpStatus.OK);

	}

}
