package com.techproed.schoolmanagementbackendb326.controller.user;

import com.techproed.schoolmanagementbackendb326.payload.request.business.AddLessonProgramForStudent;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentUpdateRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.user.StudentResponse;
import com.techproed.schoolmanagementbackendb326.service.user.StudentService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;

  @PreAuthorize("hasAnyAuthority('Admin')")
  @PostMapping("/save")
  public ResponseMessage<StudentResponse> save(@RequestBody @Valid StudentRequest studentRequest) {
    return studentService.save(studentRequest);
  }

  @PreAuthorize("hasAnyAuthority('Student')")
  @PutMapping("/update")
  public ResponseEntity<String> updateStudent(
      HttpServletRequest httpServletRequest,
      @RequestBody @Valid StudentUpdateRequest studentUpdateRequest) {
    return ResponseEntity.ok(studentService.updateStudent(httpServletRequest, studentUpdateRequest));
  }

  // TODO furkan fix this bug.
  // when we update teacher by manager, fathername and mother name saved always NULL in db.
  @PreAuthorize("hasAnyAuthority('Admin')")
  @PutMapping("/updateByAdmin/{studentId}")
  public ResponseMessage<StudentResponse>updateStudentByManager(
      @PathVariable Long studentId,
      @RequestBody @Valid StudentRequest studentRequest) {
    return studentService.updateStudentByManager(studentId,studentRequest);
  }

  //TODO Ertugrul
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/changeStatus")
  public ResponseMessage changeStatus(@RequestParam Long id,
      @RequestParam boolean status){
    //return studentService.changeStatus(id,status);
    return null;
  }

  @PreAuthorize("hasAnyAuthority('Student')")
  @GetMapping("/addLessonProgram")
  public ResponseMessage<StudentResponse>addLessonProgram(
      HttpServletRequest httpServletRequest,
      @RequestBody @Valid AddLessonProgramForStudent addLessonProgramForStudent){
    return studentService.addLessonProgram(httpServletRequest,addLessonProgramForStudent);
  }







}
