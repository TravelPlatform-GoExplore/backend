package org.anaservinovska.travelplatform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class PublicController {
    @GetMapping
    public ResponseEntity<String> HelloWorld() {
        return ResponseEntity.ok("Hello, World");
    }
}
