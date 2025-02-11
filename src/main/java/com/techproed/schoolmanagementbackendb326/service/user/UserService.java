package com.techproed.schoolmanagementbackendb326.service.user;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.payload.mappers.UserMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.user.UserRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.abstracts.BaseUserResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.user.UserResponse;
import com.techproed.schoolmanagementbackendb326.repository.user.UserRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import com.techproed.schoolmanagementbackendb326.service.validator.UniquePropertyValidator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UniquePropertyValidator uniquePropertyValidator;
  private final UserMapper userMapper;
  private final UserRepository userRepository;
  private final MethodHelper methodHelper;
  private final PageableHelper pageableHelper;


  public ResponseMessage<UserResponse> saveUser(UserRequest userRequest, String userRole) {
    //validate unique prop.
    uniquePropertyValidator.checkDuplication(
        userRequest.getUsername(),
        userRequest.getSsn(),
        userRequest.getPhoneNumber(),
        userRequest.getEmail()
    );
    //DTO->entity mapping
    User userToSave = userMapper.mapUserRequestToUser(userRequest,userRole);
    //save operation
    User savedUser = userRepository.save(userToSave);
    //entity ->DTO mapping
    return ResponseMessage.<UserResponse>builder()
        .message(SuccessMessages.USER_CREATE)
        .returnBody(userMapper.mapUserToUserResponse(savedUser))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public ResponseMessage<BaseUserResponse> findUserById(Long userId) {
    //validate if user exist in DB
    User user = methodHelper.isUserExist(userId);
    return ResponseMessage.<BaseUserResponse>builder()
        .message(SuccessMessages.USER_FOUND)
        //map entity to DTO
        .returnBody(userMapper.mapUserToUserResponse(user))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public String deleteUserById(Long userId) {
    //validate if user exist in DB
    methodHelper.isUserExist(userId);
    //delete user from BD
    userRepository.deleteById(userId);
    return SuccessMessages.USER_DELETE;
  }

  public Page<UserResponse> getUserByPage(int page, int size, String sort, String type,
      String userRole) {
    Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
    return userRepository.findUserByUserRoleQuery(userRole,pageable)
          .map(userMapper::mapUserToUserResponse);
  }

  public ResponseMessage<UserResponse> updateUserById(UserRequest userRequest, Long userId) {
      //validate if user exist
    User userFromDb = methodHelper.isUserExist(userId);
    //build in users can not be updated
    methodHelper.checkBuildIn(userFromDb);
    //validate unique properties


  }
}
