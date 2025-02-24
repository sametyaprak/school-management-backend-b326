package com.techproed.schoolmanagementbackendb326.controller.user;

import com.techproed.schoolmanagementbackendb326.payload.request.business.AddLessonProgram;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonProgramRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.user.TeacherRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.user.StudentResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.user.UserResponse;
import com.techproed.schoolmanagementbackendb326.service.user.TeacherService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

  private final TeacherService teacherService;

  @PreAuthorize("hasAnyAuthority('Admin')")
  @PostMapping("/save")
  public ResponseMessage<UserResponse>saveTeacher(
      @RequestBody @Valid TeacherRequest teacherRequest) {
    return teacherService.saveTeacher(teacherRequest);
  }


  @PreAuthorize("hasAnyAuthority('Admin')")
  @PutMapping("/update/{userId}")
  public ResponseMessage<UserResponse>updateTeacher(
      @RequestBody @Valid TeacherRequest teacherRequest,
      @PathVariable long userId){
    return teacherService.updateTeacherById(teacherRequest,userId);
  }


  //teacher will log-in then get all students who are assigned to him/her via lesson programs
  @PreAuthorize("hasAnyAuthority('Teacher')")
  @GetMapping("/getByAdvisorTeacher")
  public List<StudentResponse>getAllStudentByAdvisorTeacher(
      HttpServletRequest httpServletRequest){
    return teacherService.getAllStudentByAdvisorTeacher(httpServletRequest);
  }

  //managers can add lesson programs to teacher
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PostMapping("/addLessonProgram")
  public ResponseMessage<UserResponse>addLessonProgram(
      @RequestBody @Valid AddLessonProgram lessonProgram){
    return teacherService.addLessonProgram(lessonProgram);
  }




}
