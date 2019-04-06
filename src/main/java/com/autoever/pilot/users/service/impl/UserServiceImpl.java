package com.autoever.pilot.users.service.impl;

import com.autoever.pilot.common.exception.AlreadyExistsException;
import com.autoever.pilot.mapper.UserMapper;
import com.autoever.pilot.model.User;
import com.autoever.pilot.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    @Override
    public List<User> selectUsers() {
        return userMapper.selectUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User selectUser(String username) {
        User user = userMapper.selectUser(username);
        if(user != null) {
            user.setAuthorities(getAuthorities(username));
        }
        return user;
    }

    @Transactional
    @Override
    public void insertUser(User user) throws Exception{
        User formalUser = userMapper.selectUser(user.getUsername());
        if(formalUser != null) {
            throw new AlreadyExistsException("User already exists");
        }
        String rawPassword = user.getPassword();
        String encryptedPassword = passwordEncoder().encode(rawPassword);
        user.setPassword(encryptedPassword);

        userMapper.insertUser(user);
        userMapper.insertAuthority(user);
    }

    @Transactional
    @Override
    public int deleteUser(String username) {
        int result = userMapper.deleteUser(username);
        userMapper.deleteAuthority(username);

        return result;
    }








    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUser(username);
        if(user != null) {
            user.setAuthorities(getAuthorities(username));
            // 사용자 계정 잠김, 패스워드 만료 등 체크 가능
            return user;
        }else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities(String username) {
        List<String> stringAuths = userMapper.selectAuthorities(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        stringAuths.forEach(auth -> {
            authorities.add(new SimpleGrantedAuthority(auth));
        });

        return authorities;
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }
}
