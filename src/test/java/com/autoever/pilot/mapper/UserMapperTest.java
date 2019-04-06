package com.autoever.pilot.mapper;

import com.autoever.pilot.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserMapperTest {

    @Autowired UserMapper userMapper;

    @Test
    public void selectUserTest() {
        User user = userMapper.selectUser("kwonssy02");
        assertThat(user.getUsername()).isEqualTo("kwonssy02");
    }

    @Test
    public void selectAuthoritiesTest() {
        List<String> authorities = userMapper.selectAuthorities("kwonssy02");
//        assertThat(roles.get(0)).isEqualTo("ADMIN");
        assertThat(authorities).contains("ADMIN");
        assertThat(authorities).contains("USER");
    }
}