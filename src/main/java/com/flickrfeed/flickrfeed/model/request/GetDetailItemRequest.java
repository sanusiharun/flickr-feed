package com.flickrfeed.flickrfeed.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class GetDetailItemRequest {
	private Long itemId;
}
