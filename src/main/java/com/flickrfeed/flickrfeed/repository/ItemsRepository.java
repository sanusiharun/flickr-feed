package com.flickrfeed.flickrfeed.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.flickrfeed.flickrfeed.model.entity.Items;
import com.flickrfeed.flickrfeed.model.projection.ItemsView;

public interface ItemsRepository extends JpaRepository<Items, Long>{
	
	Optional<Items> findByTitle(String title);
	
	@Query(value = "select i.author, i.author_id as authorId, i.date_taken as dateTaken, i.title, i.link, i.published, i.tags, m.m as imageUrl from items i "
			+ "inner join flickr_feed ff on ff.id = i.flickr_feed_id "
			+ "inner join media m on m.items_id = i.id  "
			+ "where lower(CAST(i.author AS TEXT)) like COALESCE(CAST(:author AS TEXT), lower(CAST(i.author AS TEXT))) "
			+ "and lower(CAST(i.description AS TEXT)) like COALESCE(CAST(:description AS TEXT), lower(CAST(i.description AS TEXT))) "
			+ "and lower(CAST(i.link AS TEXT)) like COALESCE(CAST(:link AS TEXT), lower(CAST(i.link AS TEXT))) "
			+ "and lower(CAST(i.tags AS TEXT)) like COALESCE(CAST(:tags AS TEXT), lower(CAST(i.tags AS TEXT))) "
			+ "and lower(CAST(i.title AS TEXT)) like COALESCE(CAST(:title AS TEXT), lower(CAST(i.title AS TEXT)))", 
			countQuery = "select count(i.author) from items i "
					+ "inner join flickr_feed ff on ff.id = i.flickr_feed_id "
					+ "where lower(CAST(i.author AS TEXT)) like COALESCE(CAST(:author AS TEXT), lower(CAST(i.author AS TEXT))) "
					+ "and lower(CAST(i.description AS TEXT)) like COALESCE(CAST(:description AS TEXT), lower(CAST(i.description AS TEXT))) "
					+ "and lower(CAST(i.link AS TEXT)) like COALESCE(CAST(:link AS TEXT), lower(CAST(i.link AS TEXT))) "
					+ "and lower(CAST(i.tags AS TEXT)) like COALESCE(CAST(:tags AS TEXT), lower(CAST(i.tags AS TEXT))) "
					+ "and lower(CAST(i.title AS TEXT)) like COALESCE(CAST(:title AS TEXT), lower(CAST(i.title AS TEXT)))", nativeQuery = true)
	Page<ItemsView> findBySearchCriteria(String author, String description, String link, String tags, String title,
			Pageable pageRequest);

}
