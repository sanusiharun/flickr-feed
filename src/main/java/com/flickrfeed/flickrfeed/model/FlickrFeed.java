package com.flickrfeed.flickrfeed.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlickrFeed {

	private String title;
	private String link;
	private String description;
	private String modified;
	private String generator;
	private List<Items> items;
	
}
