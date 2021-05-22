package com.flickrfeed.flickrfeed.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAvailableFlickrFeedRequest {
	
	private String id;
	private String ids;
	private String tag;
	private String tagmode;
	private String format;
	private String lang;

}
