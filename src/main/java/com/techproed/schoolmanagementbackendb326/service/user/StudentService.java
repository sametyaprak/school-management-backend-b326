package com.techproed.schoolmanagementbackendb326.service.user;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.payload.mappers.UserMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.AddLessonProgramForStudent;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentUpdateRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.user.StudentResponse;
import com.techproed.schoolmanagementbackendb326.repository.user.UserRepository;
import com.techproed.schoolmanagementbackendb326.service.businnes.LessonProgramService;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import com.techproed.schoolmanagementbackendb326.service.validator.UniquePropertyValidator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final MethodHelper methodHelper;
  private final UniquePropertyValidator uniquePropertyValidator;
  private final UserMapper userMapper;
  private final LessonProgramService lessonProgramService;
  private final TimeValidator timeValidator;
  private final UserRepository userRepository;

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
    User student = userMapper.mapStudentRequestToUser(studentRequest);
    //set missing props
    student.setAdvisorTeacherId(advisorTeacher.getId());
    student.setActive(true);
    //every student will have a number starting from 1000.
    student.setStudentNumber(getLastStudentNumber());
    User savedStudent = userRepository.save(student);
    return ResponseMessage.<StudentResponse>
        builder()
        .returnBody(userMapper.mapUserToStudentResponse(savedStudent))
        .message(SuccessMessages.STUDENT_SAVE)
        .httpStatus(HttpStatus.CREATED)
        .build();
  }

  private int getLastStudentNumber() {
    if(!userRepository.findStudent()){
      return 1000;
    }
    return userRepository.getMaxStudentNumber()+1;
  }

  public String updateStudent(HttpServletRequest httpServletRequest,
      StudentUpdateRequest studentUpdateRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User student = methodHelper.loadByUsername(username);
    uniquePropertyValidator.checkUniqueProperty(student, studentUpdateRequest);
    User userToUpdate = userMapper.mapStudentUpdateRequestToUser(studentUpdateRequest);
    userToUpdate.setId(student.getId());
    userToUpdate.setPassword(student.getPassword());
    userToUpdate.setBuildIn(student.getBuildIn());
    userToUpdate.setAdvisorTeacherId(student.getAdvisorTeacherId());
    userRepository.save(userToUpdate);
    return SuccessMessages.STUDENT_UPDATE;
  }

  public ResponseMessage<StudentResponse> updateStudentByManager(Long studentId,
      StudentRequest studentRequest) {
    //validate user existence
    User student = methodHelper.isUserExist(studentId);
    methodHelper.checkUserRole(student,RoleType.STUDENT);
    uniquePropertyValidator.checkUniqueProperty(student, studentRequest);
    //validate advisor teacher
    User advisorTeacher = methodHelper.isUserExist(studentRequest.getAdvisorTeacherId());
    methodHelper.checkIsAdvisor(advisorTeacher);
    User studentToUpdate = userMapper.mapStudentRequestToUser(studentRequest);
    //add missing props.
    studentToUpdate.setId(student.getId());
    studentToUpdate.setActive(student.isActive());
    studentToUpdate.setStudentNumber(student.getStudentNumber());
    studentToUpdate.setLessonProgramList(student.getLessonProgramList());
    return ResponseMessage.<StudentResponse>builder()
        .message(SuccessMessages.STUDENT_UPDATE)
        .returnBody(userMapper.mapUserToStudentResponse(userRepository.save(studentToUpdate)))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public ResponseMessage<StudentResponse> addLessonProgram(HttpServletRequest httpServletRequest,
      @Valid AddLessonProgramForStudent addLessonProgramForStudent) {
    String username = (String) httpServletRequest.getAttribute("username");
    User loggedInUser = methodHelper.loadByUsername(username);
    //new lesson programs from request
    List<LessonProgram>lessonProgramFromDto =
        lessonProgramService.getLessonProgramById(addLessonProgramForStudent.getLessonProgramId());
    //existing lesson programs of student
    List<LessonProgram>studentLessonProgram = loggedInUser.getLessonProgramList();
    //TODO user LessonProgramDuplicationHelper here
    studentLessonProgram.addAll(lessonProgramFromDto);
    return null;
  }

  public ResponseMessage changeStatus(Long id, boolean status) {

    User student = methodHelper.isUserExist(id);

    methodHelper.checkUserRole(student,RoleType.STUDENT);

    student.setActive(status);

    User updateStudentUser = userRepository.save(student);

    return ResponseMessage.<StudentResponse>builder()
            .message(SuccessMessages.STUDENT_UPDATE)
            .returnBody(userMapper.mapUserToStudentResponse(updateStudentUser))
            .httpStatus(HttpStatus.OK)
            .build();

  }
}
