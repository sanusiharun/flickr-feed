package com.flickrfeed.flickrfeed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.flickrfeed.flickrfeed.model.entity.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>{

	Optional<Media> findByM(String m);

}
