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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

  //TODO NESLIHAN
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @DeleteMapping("/deleteTeacherById/{teacherId}")
  public ResponseMessage<UserResponse> deleteTeacherById(@PathVariable Long teacherId){
    return teacherService.deleteTeacherById(teacherId);
  }

  //TODO BELKIS -> not all users
  //getAllByTeacher

  //TODO KERIM
  //getAllTeacherByPage
  @PreAuthorize("hasAnyAuthority('Admin')")
  @GetMapping("/getAllTeacherByPage")
  public ResponseEntity<Page<UserResponse>> getAllTeacherByPage(
          @RequestParam(value = "page", defaultValue = "0") int page,
          @RequestParam(value = "size", defaultValue = "10") int size,
          @RequestParam(value = "sort", defaultValue = "name") String sort,
          @RequestParam(value = "type", defaultValue = "desc") String type) {

    Page<UserResponse> teacherResponses = teacherService.getAllTeacherByPage(page, size, sort, type);

    return ResponseEntity.ok(teacherResponses);
  }




}
