package com.utopian.springframework.bootstrap;

import com.utopian.springframework.domain.Venue;
import com.utopian.springframework.repositories.VenueRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author Ganesh Ramasubramanian
 */
@Slf4j
@Component
public class VenuesLoader {
    public VenuesLoader(VenueRepository venueRepository, MongoOperations operations) throws IOException {
        if (venueRepository.count() != 0) {
            return;
        }

        List<Venue> venues = readVenues();
        log.info("Importing {} stores into MongoDB…", venues.size());
        venueRepository.save(venues);
        log.info("Successfully imported {} stores.", venueRepository.count());
    }

    public static List<Venue> readVenues() throws IOException {
        return VenuesLog.fromPath("/venues.json").getVenues();
    }
}
