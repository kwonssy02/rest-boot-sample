package com.autoever.pilot.users;

import com.autoever.pilot.model.User;
import com.autoever.pilot.users.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired UserService userService;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired ModelMapper modelMapper;


    @GetMapping("/api/users")
    public ResponseEntity selectUsers() {

        List<User> users = userService.selectUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/api/users/{username}")
    public ResponseEntity selectUser(@PathVariable String username) {

        User user = userService.selectUser(username);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/users")
    public ResponseEntity createUser(@RequestBody User user) throws Exception {
        user.setAuthorities(AuthorityUtils.createAuthorityList("USER"));
        userService.insertUser(user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/users/{username}")
    public ResponseEntity deleteUser(@PathVariable String username) {

        int result = userService.deleteUser(username);
        if(result == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
