package com.utopian.springframework.bootstrap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.utopian.springframework.domain.Venue;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ganesh Ramasubramanian
 */
public class VenuesLog implements Serializable {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    @Getter
    @Setter
    private String comments;
    @Getter
    @Setter
    private List<Entry> entries = new ArrayList<>();

    public static VenuesLog fromPath(String path) throws IOException {
        return MAPPER.readValue(VenuesLog.class.getResourceAsStream(path), VenuesLog.class);
    }

    @JsonIgnore
    public List<Venue> getVenues() {
        return entries.stream().map(entry ->
                new Venue(entry.name, new Venue.Address(entry.street, entry.city, entry.zip))).collect(Collectors.toList());
    }

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_DEFAULT)
    public static class Entry implements Serializable {
        @Getter
        @Setter
        private String name;
        @Getter
        @Setter
        private String street;
        @Getter
        @Setter
        private String city;
        @Getter
        @Setter
        private String zip;
    }
}
