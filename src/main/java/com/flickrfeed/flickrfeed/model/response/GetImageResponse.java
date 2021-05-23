package com.flickrfeed.flickrfeed.model.response;

import org.springframework.core.io.Resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetImageResponse {
	private String filename;
	private String link;
	private byte[] resource;
	private Resource file;
}
