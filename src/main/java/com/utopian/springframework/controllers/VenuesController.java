
package com.utopian.springframework.controllers;

import com.utopian.springframework.domain.Venue;
import com.utopian.springframework.repositories.VenueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * A Spring MVC controller to produce an HTML frontend.
 *
 * @author Ganesh Ramasubramanian
 */
@RestController
@RequiredArgsConstructor
@Slf4j
class VenuesController {

    private static final String DEFAULT_CITY = "Wilrijk";
    private VenueRepository venueRepository;

    @RequestMapping(value = "/venues")
    public ResponseEntity<Resources<Resource>> getVenues(@RequestParam(value = "city", required = false, defaultValue = DEFAULT_CITY) String city) {
        log.info("Querying venues for the city {} from Repository", city);
        List<Venue> venues = venueRepository.findByAddressCity(city);

        // Define links to this path.
        Link link = linkTo(VenuesController.class).slash("/venues").withSelfRel();

        // Build a list of resources and add the venue.
        final List<Resource> resources = new ArrayList<>();
        venues.stream().forEach(venue -> buildVenueResource(resources, venue));

        // Wrap your resources in a Resources object.
        Resources<Resource> resourceList = new Resources<Resource>(resources, link);

        return new ResponseEntity<Resources<Resource>>(resourceList, HttpStatus.OK);
    }

    @RequestMapping(value = "/venue/{id}")
    public ResponseEntity<Resources<Resource>> getVenue(@PathVariable String id) {
        log.info("Querying Venue with ID {} from Repository", id);
        Venue venue = new Venue("testme", new Venue.Address("street", "city", "zip"));

        // Define links to this path.
        Link link = linkTo(VenuesController.class).slash("/venue").withSelfRel();

        // Build a list of resources and add the venue.
        final List<Resource> resources = new ArrayList<>();
        resources.add(new Resource<Venue>(venue, link.expand(venue.getId())));
        // Wrap your resources in a Resources object.
        Resources<Resource> resourceList = new Resources<Resource>(resources, link);

        return new ResponseEntity<Resources<Resource>>(resourceList, HttpStatus.OK);
    }

    private void buildVenueResource(List<Resource> venuesResources, Venue venue) {
        // Provide a link to lookup venue
        log.info("Venue {} details", venue);
        Link venueLink = linkTo(VenuesController.class).slash("/venue").slash(venue.getId()).withSelfRel();
        Resource<Venue> venueResource = new Resource<Venue>(venue, venueLink.expand(venue.getId()));

        venuesResources.add(venueResource);
    }
}
