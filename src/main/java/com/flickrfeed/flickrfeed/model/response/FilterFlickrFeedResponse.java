package com.flickrfeed.flickrfeed.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
@AllArgsConstructor
public class FilterFlickrFeedResponse extends BasePaginationResponse{
	List<ItemsList> content;
}
