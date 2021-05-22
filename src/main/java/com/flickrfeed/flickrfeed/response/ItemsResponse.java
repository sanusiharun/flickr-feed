package com.flickrfeed.flickrfeed.response;


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
public class ItemsResponse {
	private String title;
	private String link;
	private MediaResponse media;
	private String date_taken;
	private String description;
	private String published;
	private String author;
	private String author_id;
	private String tags;
}
