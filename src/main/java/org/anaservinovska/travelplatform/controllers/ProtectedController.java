package org.anaservinovska.travelplatform.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class ProtectedController {
    @GetMapping
    public ResponseEntity<String> HelloWorld() {
        return ResponseEntity.ok("");
    }
}
