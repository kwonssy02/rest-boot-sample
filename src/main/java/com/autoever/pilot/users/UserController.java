package com.autoever.pilot.users;

import com.autoever.pilot.model.User;
import com.autoever.pilot.users.dto.SimpleUserDTO;
import com.autoever.pilot.users.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired UserService userService;
    @Autowired AuthenticationManager authenticationManager;
    @Autowired ModelMapper modelMapper;


    @GetMapping("/api/users")
    public ResponseEntity selectUsers(@CurrentUser User currentUser) {

        List<User> users = userService.selectUsers();

        List<SimpleUserDTO> simpleUsers = users.stream().map(user ->
                modelMapper.map(user, SimpleUserDTO.class)).collect(Collectors.toList());

        return ResponseEntity.ok(simpleUsers);
    }

    @GetMapping("/api/users/{username}")
    public ResponseEntity selectUser(@PathVariable String username,
                                     @CurrentUser User currentUser) {

        User user = userService.selectUser(username);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        SimpleUserDTO simpleUser = modelMapper.map(user, SimpleUserDTO.class);

        return ResponseEntity.ok(simpleUser);
    }

    @PostMapping("/api/users")
    public ResponseEntity createUser(@RequestBody User user,
                                     @CurrentUser User currentUser) throws Exception {
        user.setAuthorities(AuthorityUtils.createAuthorityList("USER"));
        userService.insertUser(user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/users/{username}")
    public ResponseEntity deleteUser(@PathVariable String username,
                                     @CurrentUser User currentUser) {

        int result = userService.deleteUser(username);
        if(result == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
