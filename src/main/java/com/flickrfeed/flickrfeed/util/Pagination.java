package com.flickrfeed.flickrfeed.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pagination implements Serializable {
	private Integer pageSize;
	private Integer currentPage;
	private Integer totalPages;
	private Long totalRecords;
	private Boolean isFirstPage;
	private Boolean isLastPage;
}
