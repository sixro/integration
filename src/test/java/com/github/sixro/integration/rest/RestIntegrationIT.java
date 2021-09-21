package com.github.sixro.integration.rest;

import com.github.sixro.integration.Integration;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RestIntegrationIT {

    @Test public void get() {
        Integration<PersonName, PersonName> i = new PostmanEcho(new RestTemplate());

        PersonName o = i.run(new PersonName("Mario", "Rossi"));

        assertEquals("Mario", o.getFirstName());
        assertEquals("Rossi", o.getLastName());
    }


}