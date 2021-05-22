package com.flickrfeed.flickrfeed.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Items {
	private String title;
	private String link;
	private Media media;
	private String date_taken;
	private String description;
	private String published;
	private String author;
	private String author_id;
	private String tags;
}
