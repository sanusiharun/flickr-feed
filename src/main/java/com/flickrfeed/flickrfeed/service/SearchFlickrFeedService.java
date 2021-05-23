package com.flickrfeed.flickrfeed.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flickrfeed.flickrfeed.model.projection.ItemsView;
import com.flickrfeed.flickrfeed.model.request.FilterFlickrFeedRequest;
import com.flickrfeed.flickrfeed.model.response.FilterFlickrFeedResponse;
import com.flickrfeed.flickrfeed.model.response.ItemsList;
import com.flickrfeed.flickrfeed.repository.ItemsRepository;
import com.flickrfeed.flickrfeed.util.PageableUtil;

@Service
public class SearchFlickrFeedService {

	private ItemsRepository itemsRepository;

	@Autowired
	ModelMapper modelMapper;

	private static final String DEF_SORT_BY = "title";

	public SearchFlickrFeedService(ItemsRepository itemsRepository) {
		this.itemsRepository = itemsRepository;
	}
	
	public FilterFlickrFeedResponse search(FilterFlickrFeedRequest input) {
		Page<ItemsView> pageResult = getPageResultByInput(input);
		List<ItemsList> getItemList = pageResult.getContent().stream().map(item -> ItemsList.builder()
				.author(item.getAuthor())
				.authorId(item.getAuthorId())
				.description(item.getDescription())
				.dateTaken(item.getDateTaken())
				.link(item.getLink())
				.published(item.getPublished())
				.tags(item.getTags())
				.title(item.getTitle())
				.imageUrl(item.getImageUrl())
				.build()).collect(Collectors.toList());
		
		FilterFlickrFeedResponse response = FilterFlickrFeedResponse.builder().content(getItemList).build();
		response.setPagination(PageableUtil.pageToPagination(pageResult));
		return response;
	}

	Page<ItemsView> getPageResultByInput(FilterFlickrFeedRequest input) {
		String sortBy = input.getSortBy() != null && !input.getSortBy().isEmpty() ? input.getSortBy() : DEF_SORT_BY;
		Pageable pageRequest = PageableUtil.createPageRequestNative(input, null == input.getPageSize() ? 10 :input.getPageSize(), null == input.getPageNumber() ? 1 :input.getPageNumber(), sortBy, input.getSortType());
        Page<ItemsView> pageResult = null;
        
		if (!PageableUtil.isEmptyString(input.getAuthor())) {
			input.setAuthor(PageableUtil.likeClause(input.getAuthor().toLowerCase()));
		} else
			input.setAuthor(null);

		if (!PageableUtil.isEmptyString(input.getDescription())) {
			input.setDescription(PageableUtil.likeClause(input.getDescription().toLowerCase()));
		} else
			input.setDescription(null);

		if (!PageableUtil.isEmptyString(input.getLink())) {
			input.setLink(PageableUtil.likeClause(input.getLink().toLowerCase()));
		} else
			input.setLink(null);
		
		if (!PageableUtil.isEmptyString(input.getTags())) {
			input.setTags(PageableUtil.likeClause(input.getTags().toLowerCase()));
		} else
			input.setTags(null);
		
		if (!PageableUtil.isEmptyString(input.getTitle())) {
			input.setTitle(PageableUtil.likeClause(input.getTitle().toLowerCase()));
		} else
			input.setTitle(null);

        
        pageResult = itemsRepository.findBySearchCriteria(input.getAuthor(), input.getDescription(), input.getLink(), input.getTags(), input.getTitle(), pageRequest);


        return pageResult;
    }

}
