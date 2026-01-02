package com.swnam.wedding.user.service;

import com.swnam.wedding.user.dto.UserDTO;
import com.swnam.wedding.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserMapper userMapper;
  private final BCryptPasswordEncoder passwordEncoder;

  public void join(UserDTO user) {
    UserDTO newUser = user.toBuilder()
            .password(passwordEncoder.encode(user.getPassword()))
            .role("ROLE_USER")
            .build();

    userMapper.save(newUser);
  }
}