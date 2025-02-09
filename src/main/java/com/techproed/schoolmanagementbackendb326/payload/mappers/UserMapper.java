package com.techproed.schoolmanagementbackendb326.payload.mappers;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.payload.request.user.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  public User mapUserRequestToUser(UserRequest userRequest) {
    User user = User.builder()
        .username(userRequest.getUsername())
        .name(userRequest.getName())
        .surname(userRequest.getSurname())
        .password(userRequest.getPassword())
        .ssn(userRequest.getSsn())
        .birthday(userRequest.getBirthDay())
        .birthplace(userRequest.getBirthPlace())
        .phoneNumber(userRequest.getPhoneNumber())
        .gender(userRequest.getGender())
        .email(userRequest.getEmail())
        .buildIn(userRequest.getBuildIn())
        .isAdvisor(false)
        .build();
  }

}
