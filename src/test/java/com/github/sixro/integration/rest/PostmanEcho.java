package com.github.sixro.integration.rest;

import org.apache.commons.collections4.Transformer;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PostmanEcho extends RestIntegration<PersonName, Void, PostmanEcho.Response, PersonName> {

    public PostmanEcho(RestOperations restOperations) {
        super(restOperations,
            HttpMethod.GET, "https://postman-echo.com/get?foo1={firstName}&foo2={lastName}",
            newInputTransformer(),
            PostmanEcho.Response.class, newOutputTransformer());
    }

    private static Transformer<PersonName, RestInput<Void>> newInputTransformer() {
        return personName -> {
                Map<String, String> urlPlaceholders = new HashMap<>();
                urlPlaceholders.put("firstName", personName.getFirstName());
                urlPlaceholders.put("lastName", personName.getLastName());
                RestInput<Void> restInput = new RestInput<>(urlPlaceholders, Collections.emptyMap(), null);
                return restInput;
            };
    }

    private static Transformer<Response, PersonName> newOutputTransformer() {
        return it -> {
            String firstName = it.args.foo1;
            String lastName = it.args.foo2;
            return new PersonName(firstName, lastName);
        };
    }

    public static class Response {

        private Args args;

        /**
         * @deprecated In use by framework in need of instantiation by reflection
         */
        private Response() {
        }

        public Args getArgs() {
            return args;
        }

        public Response(Args args) {
            this.args = args;
        }

        private static class Args {
            private String foo1;
            private String foo2;

            /**
             * @deprecated In use by framework in need of instantiation by reflection
             */
            private Args() {
            }

            public Args(String foo1, String foo2) {
                this.foo1 = foo1;
                this.foo2 = foo2;
            }

            public String getFoo1() {
                return foo1;
            }

            public String getFoo2() {
                return foo2;
            }
        }
    }
}
