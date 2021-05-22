package com.flickrfeed.flickrfeed.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterFlickrFeedRequest extends BasePaginationRequest{
	private String title;
	private String description;
	private String author;
	private String link;
	private String tags;
	
}
