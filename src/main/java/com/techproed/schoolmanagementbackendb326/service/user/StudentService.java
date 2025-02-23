package com.techproed.schoolmanagementbackendb326.service.user;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.payload.mappers.UserMapper;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.user.StudentResponse;
import com.techproed.schoolmanagementbackendb326.repository.user.UserRepository;
import com.techproed.schoolmanagementbackendb326.service.businnes.LessonProgramService;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import com.techproed.schoolmanagementbackendb326.service.validator.UniquePropertyValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final MethodHelper methodHelper;
  private final UniquePropertyValidator uniquePropertyValidator;
  private final UserMapper userMapper;
  private final UserRepository studentRepository;
  private final LessonProgramService lessonProgramService;
  private final TimeValidator timeValidator;

  public ResponseMessage<StudentResponse> save(StudentRequest studentRequest) {
    //does advisor teacher exist in DB
    User advisorTeacher = methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());
    //is he/she really advisor
    methodHelper.checkIsAdvisor(advisorTeacher);
    //validate unique properties
    uniquePropertyValidator.checkDuplication(
        studentRequest.getUsername(),
        studentRequest.getSsn(),
        studentRequest.getPhoneNumber(),
        studentRequest.getEmail());
    //map DTO to entity
    User student = userMapper.mapUserRequestToUser(studentRequest, RoleType.STUDENT.getName());
    //set missing props
    student.setAdvisorTeacherId(advisorTeacher.getId());
    student.setActive(true);
    student.setBuildIn(false);
    //every student will have a number starting from 1000.

  }

  private int getLastStudentNumber() {

  }
}
