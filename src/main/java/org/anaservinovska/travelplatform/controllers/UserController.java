package org.anaservinovska.travelplatform.controllers;

import org.anaservinovska.travelplatform.models.UserSubscription;
import org.anaservinovska.travelplatform.services.UserService;
import org.anaservinovska.travelplatform.dto.CreateSubscriptionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/subscription/status")
    public ResponseEntity<HashMap<String,Object>> getActiveSubscription(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Optional<UserSubscription> activeSubscription = userService.getActiveSubscription(username);
        HashMap<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("subscription", activeSubscription);
        return ResponseEntity.ok(jsonObject);
    }

    @PostMapping("/subscription")
    public ResponseEntity<UserSubscription> createSubscription(
            @RequestBody CreateSubscriptionRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println(userDetails.getUsername());
        
        UserSubscription subscription = userService.createSubscription(userDetails.getUsername(), request.getSubscriptionType());
        return ResponseEntity.ok(subscription);
    }
}
