package com.autoever.pilot.accounts;

import com.autoever.pilot.model.User;
import com.autoever.pilot.users.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired UserService userService;

    private User user1;

    @Before
    public void setup() {
        user1 = new User();
        user1.setUsername("user1");
        user1.setPassword("pass1");
        user1.setEmail("user@email.com");
        user1.setAccountNonExpired(true);
        user1.setAccountNonLocked(true);
        user1.setName("USER1");
        user1.setCredentialsNonExpired(true);
        user1.setEnabled(true);
        user1.setAuthorities(AuthorityUtils.createAuthorityList("USER","ADMIN"));
    }

    @Test
    public void insertUserTest() throws Exception {
        userService.deleteUser(user1.getUsername());
        userService.insertUser(user1);
        User user = userService.selectUser(user1.getUsername());
        assertThat(user.getUsername()).isEqualTo(user1.getUsername());

        PasswordEncoder passwordEncoder = userService.passwordEncoder();
        assertThat(passwordEncoder.matches("pass1", user.getPassword())).isTrue();

        Collection<? extends GrantedAuthority> authorities1 = user1.getAuthorities();
        Iterator<? extends GrantedAuthority> it = authorities1.iterator();
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) user.getAuthorities();
        while (it.hasNext()) {
            GrantedAuthority authority = it.next();
            assertThat(authorities, hasItem(new SimpleGrantedAuthority(authority.getAuthority())));
        }
    }

}