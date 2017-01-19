package com.utopian.springframework.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.hateoas.ResourceSupport;

import java.util.UUID;

/**
 * Entity to represent a {@link Venue}.
 *
 * Note the processor class (pom.xml): it's not the QueryDSL default one com.mysema.query.apt.morphia.MorphiaAnnotationProcessor,
 * but the Spring Data MongoDB one org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor:
 *
 * Spring Data Mongo provides a custom APT processor to generate the Metamodels instead of the one provided in QueryDSL,
 * it will scan the Spring specific @Document instead of the Morphia specific annotations.
 *
 * @author Ganesh Ramasubramanian
 */
@Document
public class Venue {
    @Id
    private UUID id = UUID.randomUUID();
    @Getter @Setter String name;
    @Getter @Setter Address address;

    @JsonCreator
    public Venue(@JsonProperty("name") String name, @JsonProperty("address") Address address) {
        this.name = name;
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    /**
     * Value object to represent an {@link Address}.
     *
     * @author Ganesh Ramasubramanian
     */
    public static class Address {
        @Getter @Setter String street;
        @Getter @Setter String city;
        @Getter @Setter String zip;

        @JsonCreator
        public Address(@JsonProperty("street") String street, @JsonProperty("city") String city, @JsonProperty("zip") String zip) {
            this.street = street;
            this.city = city;
            this.zip = zip;
        }

        @Override
        public String toString() {
            return String.format("%s, %s %s", street, zip, city);
        }
    }
}
