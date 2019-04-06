package com.autoever.pilot.model;

import com.autoever.pilot.users.Role;
import lombok.*;

import java.util.Set;

@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class User {

    private String id;

    private String name;

    private String email;

    private String password;

    private Set<Role> roles;

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                '}';
    }
}
