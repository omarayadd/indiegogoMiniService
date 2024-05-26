package com.postgresql.indiegogo.commands;

import com.postgresql.indiegogo.ApiResponse;
import com.postgresql.indiegogo.MyService;
import com.postgresql.indiegogo.User;
import com.postgresql.indiegogo.UserRepo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class SignInCommand implements Command {
    private final UserRepo userRepo;
    private final MyService myService;
    private final Map<String, String> credentials;
    private static final String SECRET_KEY = "yourSecretKeykosmak3sg4gy4gggg66666gggsyys5yh5hyhg5h5h5yourSecretyourSecretKeykosmak3sg4gy4gggg66666gggsyys5yh5hyhg5h5h5yourSecret";

    @Autowired
    public SignInCommand(UserRepo userRepo, MyService myService, Map<String, String> credentials) {
        this.userRepo = userRepo;
        this.myService = myService;
        this.credentials = credentials;
    }

    @Override
    public ApiResponse execute() {
        String email = credentials.get("email");
        String password = credentials.get("passwordHash");
        User user = userRepo.findByEmail(email);

        if (user != null && user.getPasswordHash().equals(password)) {
            String token = generateToken(email);
            myService.setValue(email, token);

            return new ApiResponse("Sign-in successful", "Token: " + token);
        } else {
            return new ApiResponse("Invalid email or password", null);
        }
    }

    private String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }
}
