package com.flickrfeed.flickrfeed.model.entity;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flickr_feed")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlickrFeed {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String link;
	
	@Column(columnDefinition = "text")
	private String description;
	private String modified;
	private String generator;
	
}
