package com.postgresql.indiegogo.commands;

import com.postgresql.indiegogo.ApiResponse;
import com.postgresql.indiegogo.User;
import com.postgresql.indiegogo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class UpdateUserCommand implements Command {
    private final UserRepo userRepo;
    private final String email;
    private final Map<String, String> updateRequest;

    @Autowired
    public UpdateUserCommand(UserRepo userRepo, String email, Map<String, String> updateRequest) {
        this.userRepo = userRepo;
        this.email = email;
        this.updateRequest = updateRequest;
    }

    @Override
    public ApiResponse execute() {
        User user = userRepo.findByEmail(email);

        if (user == null) {
            return new ApiResponse("User not found", null);
        }

        updateRequest.forEach((key, value) -> {
            switch (key) {
                case "passwordHash":
                    user.setPasswordHash(value);
                    break;
                case "firstName":
                    user.setFirstName(value);
                    break;
                case "lastName":
                    user.setLastName(value);
                    break;
                case "country":
                    user.setCountry(value);
                    break;
                case "city":
                    user.setCity(value);
                    break;
                case "postalCode":
                    user.setPostalCode(value);
                    break;
                case "bio":
                    user.setBio(value);
                    break;
                case "profilePictureUrl":
                    user.setProfilePictureUrl(value);
                    break;
                case "facebookLink":
                    user.setFacebookLink(value);
                    break;
                case "twitterLink":
                    user.setTwitterLink(value);
                    break;
                case "youtubeLink":
                    user.setYoutubeLink(value);
                    break;
                case "imdbLink":
                    user.setImdbLink(value);
                    break;
                case "websiteLink":
                    user.setWebsiteLink(value);
                    break;
            }
        });

        userRepo.save(user);
        return new ApiResponse("User information updated successfully.", user);
    }
}
