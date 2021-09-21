package com.github.sixro.integration.rest;

import java.util.Map;

public class RestInput<P> {

    private final Map<String, String> urlPlaceholders;
    private final Map<String, String> headers;
    private final P payload;

    public RestInput(Map<String, String> urlPlaceholders, Map<String, String> headers, P payload) {
        this.urlPlaceholders = urlPlaceholders;
        this.headers = headers;
        this.payload = payload;
    }

    public Map<String, String> getUrlPlaceholders() {
        return urlPlaceholders;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public P getPayload() {
        return payload;
    }
}
