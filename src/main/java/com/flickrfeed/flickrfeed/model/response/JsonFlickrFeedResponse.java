package com.flickrfeed.flickrfeed.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonFlickrFeedResponse {

	private String title;
	private String link;
	private String description;
	private String modified;
	private String generator;
	private List<ItemsResponse> items;
	
}
