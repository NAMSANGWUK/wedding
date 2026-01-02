package com.swnam.wedding.user.service;

import com.swnam.wedding.user.dto.PrincipalDetails;
import com.swnam.wedding.user.dto.UserDTO;
import com.swnam.wedding.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // DB에서 이메일로 사용자 조회 (아이디 찾기)
    UserDTO user = userMapper.findByEmail(username);

    if (user == null) {
      throw new UsernameNotFoundException("아이디가 존재하지 않습니다 : " + username);
    }

    // 통합 신분증에 담아서 반환 (비번 비교는 시큐리티가 알아서 함)
    return new PrincipalDetails(user);
  }
}