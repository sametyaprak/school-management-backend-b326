package com.techproed.schoolmanagementbackendb326.service.validator;

import com.techproed.schoolmanagementbackendb326.exception.ConflictException;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
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
    if(userRepository.existsByUsername(username)) {
      throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_USERNAME, username));
    }
    if(userRepository.existsByEmail(email)) {
      throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_EMAIL, email));
    }
    if(userRepository.existsByPhoneNumber(phone)) {
      throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER, phone));
    }
    if(userRepository.existsBySsn(ssn)) {
      throw new ConflictException(String.format(ErrorMessages.ALREADY_REGISTER_MESSAGE_SSN, ssn));
    }

  }

}
