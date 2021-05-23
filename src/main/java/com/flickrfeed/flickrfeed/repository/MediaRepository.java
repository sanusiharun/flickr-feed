package com.flickrfeed.flickrfeed.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.flickrfeed.flickrfeed.model.entity.Media;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long>{

	Optional<Media> findByM(String m);
	
	@Query(value = "SELECT m.id, m.m, m.items_id from media m inner join items i on m.items_id = i.id where i.title = ?1 LIMIT 1", nativeQuery = true)
	Media findByItemTitle(String title);
	
	@Query(value = "SELECT m.id, m.m, m.items_id from media m inner join items i on m.items_id = i.id", nativeQuery = true)
	List<Media> findAllImage();

}
