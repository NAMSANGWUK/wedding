package com.swnam.wedding.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDTO {
  private Long userId;
  private String email;
  private String password;
  private String name;
  private String provider;
  private String providerId;
  private String role;          // 권한

  private String postcode;      // 우편번호
  private String address;       // 기본주소
  private String detailAddress; // 상세주소
}
