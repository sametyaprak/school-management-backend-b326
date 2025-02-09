package com.techproed.schoolmanagementbackendb326.service.validator;

import com.techproed.schoolmanagementbackendb326.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniquePropertyValidator {

  private final UserRepository userRepository;


  public void checkDuplication(
      String username,
      String ssn,
      String phone,
      String email) {

  }

}
