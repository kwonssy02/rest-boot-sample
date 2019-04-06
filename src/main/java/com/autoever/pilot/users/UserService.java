package com.autoever.pilot.users;

import com.autoever.pilot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    public User saveAccount(User user) {
        User newUser = User.builder()
                .id("kwonssy02")
                .email("kwonssy02@gmail.com")
                .build();
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        System.out.println("save!");
        return newUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = User.builder()
                .id("kwonssy02")
                .email("kwonssy02@gmail.com")
                .build();
        return new UserAdapter(user);
    }

    private Collection<? extends GrantedAuthority> authorities(Set<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect((Collectors.toSet()));
    }
}
