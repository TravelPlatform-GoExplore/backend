package org.anaservinovska.travelplatform.services;

import org.anaservinovska.travelplatform.dto.RegisterRequest;
import org.anaservinovska.travelplatform.models.TravelPlatformUser;
import org.anaservinovska.travelplatform.models.UserSubscription;
import org.anaservinovska.travelplatform.models.UserSubscriptionType;
import org.anaservinovska.travelplatform.repositories.UserRepository;
import org.anaservinovska.travelplatform.repositories.UserSubscriptionRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public UserService(UserRepository userRepository, 
                      PasswordEncoder passwordEncoder,
                      UserSubscriptionRepository userSubscriptionRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    public String registerUser(RegisterRequest registerRequest) {
        // Check if the username already exists
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return "Username already exists";
        }

        // Encrypt the password
        String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Create a new user
        TravelPlatformUser travelPlatformUser = new TravelPlatformUser();
        travelPlatformUser.setUsername(registerRequest.getUsername());
        travelPlatformUser.setEmail(registerRequest.getEmail());
        travelPlatformUser.setPassword(encryptedPassword);
//        user.setRoles(Arrays.asList(registerRequest.getRoles()));  // Add roles to the user

        // Save the user to the database
        userRepository.save(travelPlatformUser);

        return "User registered successfully";
    }

    public TravelPlatformUser findByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    public Optional<UserSubscription> getActiveSubscription(String username) {
        return userRepository.findByUsername(username)
                .map(userSubscriptionRepository::findActiveSubscriptionForUser)
                .orElse(null);
    }

    public UserSubscription createSubscription(String username, UserSubscriptionType subscriptionType) {
        TravelPlatformUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        // Deactivate existing subscription
        Optional<UserSubscription> existingSubscription = userSubscriptionRepository.findByUserAndIsActiveTrue(user);
        existingSubscription.ifPresent(subscription -> {
            subscription.setActive(false);
            subscription.setEndDate(LocalDateTime.now());
            userSubscriptionRepository.save(subscription);
        });

        // Create new subscription
        UserSubscription newSubscription = new UserSubscription();
        newSubscription.setUser(user);
        newSubscription.setSubscriptionType(subscriptionType);
        newSubscription.setStartDate(LocalDateTime.now());
        newSubscription.setActive(true);

        // Flush the persistence context to ensure proper ID generation
        userSubscriptionRepository.flush();
        return userSubscriptionRepository.save(newSubscription);
    }
}
