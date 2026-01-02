package com.swnam.wedding.user.service;

import com.swnam.wedding.user.dto.PrincipalDetails;
import com.swnam.wedding.user.dto.UserDTO;
import com.swnam.wedding.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserMapper userMapper;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2User oAuth2User = super.loadUser(userRequest);

    // 어느 소셜 로그인인지 구분 (google, naver, kakao)
    String provider = userRequest.getClientRegistration().getRegistrationId();

    String providerId = "";
    String email = "";
    String name = "";

    if (provider.equals("google")) {
      providerId = oAuth2User.getAttribute("sub");
      email = oAuth2User.getAttribute("email");
      name = oAuth2User.getAttribute("name");
    }
    else if (provider.equals("naver")) {
      Map<String, Object> response = oAuth2User.getAttribute("response");
      if (response != null) {
        providerId = String.valueOf(response.get("id"));
        email = String.valueOf(response.get("email"));
        name = String.valueOf(response.get("name"));
      }
    }
    else if (provider.equals("kakao")) {
      Object idObj = oAuth2User.getAttribute("id");
      providerId = String.valueOf(idObj);

      Map<String, Object> account = oAuth2User.getAttribute("kakao_account");
      if (account != null) {
        email = String.valueOf(account.get("email"));
        Map<String, Object> profile = (Map<String, Object>) account.get("profile");
        if (profile != null) {
          name = String.valueOf(profile.get("nickname"));
        }
      }
    }

    if (email == null || email.isEmpty() || email.equals("null")) {
      email = provider + "_" + providerId + "@wedding-member.com";
    }

    if (name == null || name.isEmpty() || name.equals("null")) {
      name = "임시회원";
    }

    UserDTO userDTO = userMapper.findByProvider(provider, providerId);

    if (userDTO == null) {
      // 신규 가입
      userDTO = UserDTO.builder()
              .email(email)
              .name(name)
              .provider(provider)
              .providerId(providerId)
              .role("ROLE_USER")
              .build();
      userMapper.save(userDTO);
    } else {
      if (!Objects.equals(userDTO.getName(), name) || !Objects.equals(userDTO.getEmail(), email)) {
        UserDTO updatedUser = userDTO.toBuilder()
                .name(name)
                .email(email)
                .build();
        userMapper.update(updatedUser);
      }
    }
    return new PrincipalDetails(userDTO, oAuth2User.getAttributes());
  }
}
