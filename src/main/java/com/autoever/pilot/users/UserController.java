package com.autoever.pilot.users;

import com.autoever.pilot.mapper.UserMapper;
import com.autoever.pilot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public ResponseEntity selectUsers() {

        List<User> users = userMapper.selectUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity selectUser(@PathVariable String id) {

        User user = userMapper.selectUser(id);

        if(user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {

        userMapper.insertUser(user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {

        int result = userMapper.deleteUser(id);
        if(result == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
