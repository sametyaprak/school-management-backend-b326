package com.techproed.schoolmanagementbackendb326.controller.user;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    return studentService.updateStudent(httpServletRequest, studentUpdateRequest);
  }


}
