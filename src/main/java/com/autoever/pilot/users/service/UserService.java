package com.autoever.pilot.users.service;

import com.autoever.pilot.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

public interface UserService extends UserDetailsService {

    /**
     * Spring Security 권한 가져오기 위한 메소드
     * @param username
     * @return
     */
    Collection<GrantedAuthority> getAuthorities(String username);

    PasswordEncoder passwordEncoder();

    List<User> selectUsers();

    User selectUser(String username);

    void insertUser(User user) throws Exception;

    int deleteUser(String username);
}
