package com.postgresql.indiegogo;

import com.postgresql.indiegogo.commands.Command;
import com.postgresql.indiegogo.commands.SignUpCommand;
import com.postgresql.indiegogo.commands.SignInCommand;
import com.postgresql.indiegogo.commands.UpdateUserCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepo repo;

    @Autowired
    private MyService myService;

    @PostMapping("/signUp")
    public ApiResponse signUp(@RequestBody Map<String, String> signUpRequest) {
        Command command = new SignUpCommand(repo, signUpRequest);
        return command.execute();
    }

    @PostMapping("/signIn")
    public ApiResponse signIn(@RequestBody Map<String, String> credentials) {
        Command command = new SignInCommand(repo, myService, credentials);
        return command.execute();
    }

    @PostMapping("/updateInfo/{email}")
    public ApiResponse updateInfo(@PathVariable String email, @RequestBody Map<String, String> updateRequest) {
        Command command = new UpdateUserCommand(repo, email, updateRequest);
        return command.execute();
    }

    @DeleteMapping("/deleteAllUsers")
    public ApiResponse deleteAllUsers() {
        repo.deleteAll();
        return new ApiResponse("All users deleted successfully", null);
    }

    @GetMapping("/getAllUsers")
    public ApiResponse getAllUsers() {
        return new ApiResponse("Users retrieved successfully", repo.findAll());
    }

    @GetMapping("/cache")
    public Map<String, String> viewCache() {
        return myService.getAllData();
    }

    @PostMapping("/logOut/{email}")
    public ApiResponse logOut(@PathVariable String email) {
        if (myService.getValue(email) != null) {
            myService.removeValue(email);
            return new ApiResponse("LogOut Successful", null);
        } else {
            return new ApiResponse("User is not logged in", null);
        }
    }
}