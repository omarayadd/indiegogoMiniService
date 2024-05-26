package com.postgresql.indiegogo.commands;

import com.postgresql.indiegogo.ApiResponse;
import com.postgresql.indiegogo.User;
import com.postgresql.indiegogo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class SignUpCommand implements Command {
    private final UserRepo userRepo;
    private final Map<String, String> signUpRequest;

    @Autowired
    public SignUpCommand(UserRepo userRepo, Map<String, String> signUpRequest) {
        this.userRepo = userRepo;
        this.signUpRequest = signUpRequest;
    }

    @Override
    public ApiResponse execute() {
        String[] requiredFields = { "email", "passwordHash", "firstName", "lastName" };
        boolean flag = true;

        for (String field : requiredFields) {
            if (!signUpRequest.containsKey(field)) {
                flag = false;
                break;
            }
        }

        if (!flag || signUpRequest.size() > 4) {
            return new ApiResponse("Error: Invalid input", null);
        }

        String email = signUpRequest.get("email");
        String passwordHash = signUpRequest.get("passwordHash");
        String firstName = signUpRequest.get("firstName");
        String lastName = signUpRequest.get("lastName");

        User user = new User(email, passwordHash, firstName, lastName);
        userRepo.save(user);
        return new ApiResponse("User added successfully.", user);
    }
}
