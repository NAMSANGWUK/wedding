package com.swnam.wedding.user.controller;

import com.swnam.wedding.user.dto.UserDTO;
import com.swnam.wedding.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  // 2. 회원가입 처리
  @PostMapping("/signup")
  public String signup(UserDTO userDTO) {
    userService.join(userDTO);
    return "user/login";
  }
}
