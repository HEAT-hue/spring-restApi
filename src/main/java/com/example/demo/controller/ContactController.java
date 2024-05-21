package com.example.demo.controller;

import com.example.demo.model.Contact;
import com.example.demo.model.Response;
import com.example.demo.proxy.ContactProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ContactController {

    // REST API request
    @Autowired
    ContactProxy contactProxy;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebClient webClient;

    @GetMapping("/getMessages")
    List<Contact> getMessages(@RequestParam("status") String status) {
        List<Contact> contacts = contactProxy.getMessageByStatus(status);
        System.out.println(contacts);
        return contacts;
    }

    // Deprecated
    @PostMapping("/saveMsg")
    public ResponseEntity<Response> saveMsg(@RequestBody Contact contact) {
        // REST URL
        String uri = "http://localhost:8080/api/contact/saveMsg";

        System.out.println(contact);

        // Add HTTP headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("invocationFrom", "RestTemplate");

        // Save headers and payload
        HttpEntity<Contact> httpEntity = new HttpEntity<>(contact, httpHeaders);

        // Launch request
        return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Response.class);
    }

    // recommended
    @PostMapping("/saveMessage")
    public Mono<Response> saveMessage(@RequestBody Contact contact) {
        // POST REQ to external REST API
        String uri = "http://localhost:8080/api/contact/saveMsg";

        return webClient
                // Build the POST request
                .post().uri(uri)
                // Add headers if any
                .header("invocationFrom", "WebClient")
                // set request body with Contact object - since web client is non-blocking and expects a publisher like Mono.
                // Mono.just() creates a Mono containing contact object
                // Contcact.class to indicate type of object, likely for JSON serialization
                .body(Mono.just(contact), Contact.class)
                // consume API
                .retrieve()
                // convert response body to Response class type
                .bodyToMono(Response.class);
    }
}
