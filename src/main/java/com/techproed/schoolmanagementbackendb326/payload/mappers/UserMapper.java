package com.techproed.schoolmanagementbackendb326.payload.mappers;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.abstracts.BaseUserRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentUpdateRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.user.StudentResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.user.UserResponse;
import com.techproed.schoolmanagementbackendb326.service.user.UserRoleService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final UserRoleService userRoleService;

  private final PasswordEncoder passwordEncoder;

  /**
   *
   * @param userRequest DTO from postman or FE.
   * @param userRole role of user to be created or updated
   * @return User entity
   */
  public User mapUserRequestToUser(BaseUserRequest userRequest, String userRole) {
    User user = User.builder()
        .username(userRequest.getUsername())
        .name(userRequest.getName())
        .surname(userRequest.getSurname())
        .password(passwordEncoder.encode(userRequest.getPassword()))
        .ssn(userRequest.getSsn())
        .birthday(userRequest.getBirthDay())
        .birthplace(userRequest.getBirthPlace())
        .phoneNumber(userRequest.getPhoneNumber())
        .gender(userRequest.getGender())
        .email(userRequest.getEmail())
        .buildIn(userRequest.getBuildIn())
        .isAdvisor(false)
        .build();
    //rol ile user one to one relationship'e sahip oldugu icin
    //bunu DB'den fetch edip requeste eklememiz gerekir.
    if(userRole.equalsIgnoreCase(RoleType.ADMIN.getName())){
      //eger username ismi Admin ise datalar degistirilemez
      if(Objects.equals(userRequest.getUsername(),"Admin")){
        user.setBuildIn(true);
      }
      user.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
    } else if (userRole.equalsIgnoreCase(RoleType.MANAGER.getName())) {
      user.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
    }
    else if (userRole.equalsIgnoreCase(RoleType.ASSISTANT_MANAGER.getName())) {
      user.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANT_MANAGER));
    }
    else if (userRole.equalsIgnoreCase(RoleType.STUDENT.getName())) {
      user.setUserRole(userRoleService.getUserRole(RoleType.STUDENT));
    }
    else if (userRole.equalsIgnoreCase(RoleType.TEACHER.getName())) {
      user.setUserRole(userRoleService.getUserRole(RoleType.TEACHER));
    }
    else {
      throw new ResourceNotFoundException(
          String.format(ErrorMessages.NOT_FOUND_USER_USER_ROLE_MESSAGE, userRole));
    }
    return user;
  }

  public UserResponse mapUserToUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .username(user.getUsername())
        .name(user.getName())
        .surname(user.getSurname())
        .phoneNumber(user.getPhoneNumber())
        .gender(user.getGender())
        .birthDay(user.getBirthday())
        .birthPlace(user.getBirthplace())
        .ssn(user.getSsn())
        .email(user.getEmail())
        .userRole(user.getUserRole().getRoleType().name())
        .build();
  }


  public StudentResponse mapUserToStudentResponse(User student) {
    return StudentResponse.builder()
        .id(student.getId())
        .username(student.getUsername())
        .name(student.getName())
        .surname(student.getSurname())
        .birthDay(student.getBirthday())
        .ssn(student.getSsn())
        .birthPlace(student.getBirthplace())
        .phoneNumber(student.getPhoneNumber())
        .gender(student.getGender())
        .email(student.getEmail())
        .studentNumber(student.getStudentNumber())
        .motherName(student.getMotherName())
        .fatherName(student.getFatherName())
        .lessonProgramList(student.getLessonProgramList())
        .isActive(student.isActive())
        .build();
  }

  public User mapStudentUpdateRequestToUser(StudentUpdateRequest studentUpdateRequest){
    return User.builder()
        .username(studentUpdateRequest.getUsername())
        .name(studentUpdateRequest.getName())
        .ssn(studentUpdateRequest.getSsn())
        .userRole(userRoleService.getUserRole(RoleType.STUDENT))
        .surname(studentUpdateRequest.getSurname())
        .birthday(studentUpdateRequest.getBirthDay())
        .birthplace(studentUpdateRequest.getBirthPlace())
        .phoneNumber(studentUpdateRequest.getPhoneNumber())
        .gender(studentUpdateRequest.getGender())
        .email(studentUpdateRequest.getEmail())
        .fatherName(studentUpdateRequest.getFatherName())
        .motherName(studentUpdateRequest.getMotherName())
        .build();
  }

  public User mapStudentRequestToUser(
              StudentRequest studentRequest) {
    return User.builder()
                       .username(studentRequest.getUsername())
                       .name(studentRequest.getName())
                       .surname(studentRequest.getSurname())
                       .password(passwordEncoder.encode(studentRequest.getPassword()))
                       .birthday(studentRequest.getBirthDay())
                       .ssn(studentRequest.getSsn())
                       .birthplace(studentRequest.getBirthPlace())
                       .phoneNumber(studentRequest.getPhoneNumber())
                       .gender(studentRequest.getGender())
                       .email(studentRequest.getEmail())
                       .buildIn(false)
                       .motherName(studentRequest.getMotherName())
                       .fatherName(studentRequest.getFatherName())
                       .advisorTeacherId(studentRequest.getAdvisorTeacherId())
                       .userRole(userRoleService.getUserRole(RoleType.STUDENT))
                       .build();
  }



}
