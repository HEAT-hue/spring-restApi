package com.example.demo.proxy;

import com.example.demo.config.ProjectConfiguration;
import com.example.demo.model.Contact;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// Specify base URL here
@FeignClient(name = "contact", url = "http://localhost:8080/api/contact", configuration = ProjectConfiguration.class)
public interface ContactProxy {

    // Get message by status
    @GetMapping("/getMessagesByStatus")
    public List<Contact> getMessageByStatus(@RequestParam("status") String status);
}