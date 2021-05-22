package com.flickrfeed.flickrfeed.model.response;

import com.flickrfeed.flickrfeed.util.Pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasePaginationResponse {
	
	private Pagination pagination;
}
