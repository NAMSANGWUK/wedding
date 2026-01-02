package com.swnam.wedding.user.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

  private UserDTO user; // 우리 DB에 저장된 유저 정보
  private Map<String, Object> attributes; // 소셜 로그인 시 받는 원본 데이터

  // 일반 로그인용 생성자
  public PrincipalDetails(UserDTO user) {
    this.user = user;
  }

  // 소셜 로그인용 생성자
  public PrincipalDetails(UserDTO user, Map<String, Object> attributes) {
    this.user = user;
    this.attributes = attributes;
  }

  /** OAuth2User 구현 (소셜용) **/
  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public String getName() {
    return user.getName(); // 타임리프에서 쓸 이름
  }

  /** UserDetails 구현 (일반용) **/
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    Collection<GrantedAuthority> collect = new ArrayList<>();
    collect.add(() -> user.getRole()); // 사용자의 권한(ROLE_USER 등) 반환
    return collect;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
  }

  // 계정 상태 관리 (일단 모두 true로 설정)
  @Override public boolean isAccountNonExpired() { return true; }
  @Override public boolean isAccountNonLocked() { return true; }
  @Override public boolean isCredentialsNonExpired() { return true; }
  @Override public boolean isEnabled() { return true; }
}