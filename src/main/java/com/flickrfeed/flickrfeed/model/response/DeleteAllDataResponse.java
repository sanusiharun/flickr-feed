package com.flickrfeed.flickrfeed.model.response;


import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
public class DeleteAllDataResponse {
	private String status;
	
}
