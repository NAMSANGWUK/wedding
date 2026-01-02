package com.swnam.wedding.user.mapper;

import com.swnam.wedding.user.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
  // 소셜 정보로 기존 회원인지 확인
  UserDTO findByProvider(@Param("provider") String provider, @Param("providerId") String providerId);

  UserDTO findByEmail(@Param("email") String email);

  // 신규 회원 가입
  void save(UserDTO userDTO);

  // 기존 회원 정보 업데이트 (이름 변경 등)
  void update(UserDTO userDTO);
}
