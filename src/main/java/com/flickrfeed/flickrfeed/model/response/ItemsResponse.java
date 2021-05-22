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
public class ItemsResponse {
	private String title;
	private String link;
	private MediaResponse media;
	@JsonProperty("date_taken")
	private String dateTaken;
	private String description;
	private String published;
	private String author;
	@JsonProperty("author_id")
	private String authorId;
	private String tags;
}
