package com.techproed.schoolmanagementbackendb326.service.helper;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MethodHelper {

  private final UserRepository userRepository;


  public User isUserExist(Long id) {
    return userRepository.findById(id)
        .orElseThrow(()->new ResourceNotFoundException(
            String.format(ErrorMessages.NOT_FOUND_USER_MESSAGE, id)));
  }

}
