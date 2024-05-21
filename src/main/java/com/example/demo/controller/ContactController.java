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

import java.util.List;

@RestController
public class ContactController {

    // REST API request
    @Autowired
    ContactProxy contactProxy;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/getMessages")
    List<Contact> getMessages(@RequestParam("status") String status) {
        List<Contact> contacts = contactProxy.getMessageByStatus(status);
        System.out.println(contacts);
        return contacts;
    }

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
        ResponseEntity<Response> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Response.class);
        return responseEntity;
    }
}
