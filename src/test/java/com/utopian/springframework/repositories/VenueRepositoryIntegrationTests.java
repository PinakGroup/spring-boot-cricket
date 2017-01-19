package com.utopian.springframework.repositories;

import com.utopian.springframework.CricketApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Integration tests for {@link VenueRepository}
 *
 * @author Ganesh Ramasubramanian
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CricketApplication.class)
public class VenueRepositoryIntegrationTests {
    @Autowired
    VenueRepository venueRepository;

    @Test
    public void findByCityNear() {

    }

}