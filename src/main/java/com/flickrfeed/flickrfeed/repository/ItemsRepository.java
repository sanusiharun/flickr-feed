package com.flickrfeed.flickrfeed.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flickrfeed.flickrfeed.model.entity.Items;

public interface ItemsRepository extends JpaRepository<Items, Long>{

	Optional<Items> findByTitle(String title);

}
