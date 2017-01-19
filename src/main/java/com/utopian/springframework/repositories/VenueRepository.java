package com.utopian.springframework.repositories;

import com.querydsl.core.types.dsl.StringPath;
import com.utopian.springframework.domain.QVenue;
import com.utopian.springframework.domain.Venue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Repository interface for out-of-the-box paginating access to {@link Venue}s and a query method to find venue by city.
 *
 * @author Ganesh Ramasubramanian
 */
public interface VenueRepository extends PagingAndSortingRepository<Venue, String>, QueryDslPredicateExecutor<Venue>,
        QuerydslBinderCustomizer<QVenue> {

    @RestResource(rel = "by-city")
    public List<Venue> findByAddressCity(String city);

    /**
     * Tweak the Querydsl binding if collection resources are filtered.
     *
     * @see org.springframework.data.web.querydsl.QuerydslBinderCustomizer#customize(org.springframework.data.web.querydsl.QuerydslBindings,
     * com.mysema.query.types.EntityPath)
     */
    default void customize(QuerydslBindings bindings, QVenue venue) {
        bindings.bind(venue.address.city).first((path, value) -> path.endsWith(value));
        bindings.bind(String.class).first((StringPath path, String value) -> path.contains(value));
    }

/*
    @RestResource(rel = "by-id")
    public Venue findById(String id);
*/
}
