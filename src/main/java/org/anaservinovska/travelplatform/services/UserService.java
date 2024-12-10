package org.anaservinovska.travelplatform.services;

import org.anaservinovska.travelplatform.dto.RegisterRequest;
import org.anaservinovska.travelplatform.models.TravelPlatformUser;
import org.anaservinovska.travelplatform.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}
