package com.flickrfeed.flickrfeed.model.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemsList {
	private String title;
	private String link;
	private String dateTaken;
	private String description;
	private String published;
	private String author;
	private String authorId;
	private String tags;
}
