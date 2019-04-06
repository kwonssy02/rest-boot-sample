package com.autoever.pilot.users;

import com.autoever.pilot.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserAdapter extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserAdapter(User user) {

        super(user.getEmail(), user.getPassword(), authorities(null));
        this.user = user;
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<Role> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.name()))
                .collect((Collectors.toSet()));
    }

    public User getUser() {
        return user;
    }
}
