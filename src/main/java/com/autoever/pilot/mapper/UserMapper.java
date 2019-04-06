package com.autoever.pilot.mapper;

import com.autoever.pilot.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username = #{username}")
    User selectUser(@Param("username") String username);

    @Select("SELECT * FROM users")
    List<User> selectUsers();

    void insertUser(User user);

    @Delete("DELETE FROM users where username = #{username}")
    int deleteUser(String username);


    List<String> selectAuthorities(String username);

    void insertAuthority(User user);

    int deleteAuthority(String username);
}
