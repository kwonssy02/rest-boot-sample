package com.autoever.pilot.mapper;

import com.autoever.pilot.model.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE id = #{id}")
    User selectUser(@Param("id") String id);

    @Select("SELECT * FROM users")
    List<User> selectUsers();

    void insertUser(User user);

    @Delete("DELETE FROM users where id = #{id}")
    int deleteUser(String id);
}
