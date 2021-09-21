package com.github.sixro.integration.rest;

import com.github.sixro.integration.Integration;
import org.apache.commons.collections4.Transformer;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class RestIntegration<I, P, RESP, O> implements Integration<I, O> {

    private final RestOperations restOperations;
    private final HttpMethod method;
    private final String urlTemplate;
    private final Transformer<I, RestInput<P>> inputToRequest;
    private final Class<RESP> responseType;
    private final Transformer<RESP, O> responseToOutput;

    public RestIntegration(RestOperations restOperations, HttpMethod method, String urlTemplate, Transformer<I, RestInput<P>> inputToRequest, Class<RESP> responseType,
                           Transformer<RESP, O> responseToOutput) {
        this.restOperations = restOperations;
        this.method = method;
        this.urlTemplate = urlTemplate;
        this.inputToRequest = inputToRequest;
        this.responseType = responseType;
        this.responseToOutput = responseToOutput;
    }

    @Override
    public O run(I input) {
        RestInput<P> restInput = inputToRequest.transform(input);
        URI uri = UriComponentsBuilder.fromUriString(urlTemplate)
            .build(restInput.getUrlPlaceholders());
        HttpEntity<P> httpEntity = new HttpEntity<P>(restInput.getPayload());
        restInput.getHeaders().forEach(httpEntity.getHeaders()::add);
        ResponseEntity<RESP> responseEntity = restOperations.exchange(uri, method, httpEntity, responseType);
        // FIXME handle errors
        RESP response = responseEntity.getBody();
        O output = responseToOutput.transform(response);
        return output;
    }

}
