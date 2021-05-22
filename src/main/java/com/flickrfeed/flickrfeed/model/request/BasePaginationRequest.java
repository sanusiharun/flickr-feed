package com.flickrfeed.flickrfeed.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BasePaginationRequest {

	private Integer pageSize;
	private Integer pageNumber;
	private String sortType;
	private String sortBy;
}
