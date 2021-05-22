package com.flickrfeed.flickrfeed.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.flickrfeed.flickrfeed.model.response.MediaResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Items {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String link;
	@Column(name = "date_taken")
	private String dateTaken;

	@Column(columnDefinition = "text")
	private String description;
	private String published;
	private String author;

	@Column(name = "author_id", columnDefinition = "text")
	private String authorId;
	@Column(columnDefinition = "text")
	private String tags;

	@Column(name = "flickr_feed_id")
	private Long flickrFeedId;
	
	@Transient
	private MediaResponse media;
}
