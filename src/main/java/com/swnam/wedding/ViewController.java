package com.swnam.wedding;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

  @GetMapping("/")
  public String mainPage() {
    return "main/main";
  }

  @GetMapping("/login")
  public String loginPage() {
    return "user/login";
  }

  @GetMapping("/signup")
  public String signupPage() {
    return "user/signup"; // templates/user/signup.html
  }
}
