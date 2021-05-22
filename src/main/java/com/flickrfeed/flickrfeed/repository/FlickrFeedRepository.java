package com.flickrfeed.flickrfeed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flickrfeed.flickrfeed.model.entity.FlickrFeed;

@Repository
public interface FlickrFeedRepository extends JpaRepository<FlickrFeed, Long>{

	boolean existsByTitle(String title);

	Optional<FlickrFeed> findByTitle(String title);

}
