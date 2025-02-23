package com.techproed.schoolmanagementbackendb326.payload.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import com.techproed.schoolmanagementbackendb326.payload.response.abstracts.BaseUserResponse;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@JsonInclude(Include.NON_NULL)
public class StudentResponse extends BaseUserResponse {

  private List<LessonProgram>lessonProgramList;
  private int studentNumber;
  private String motherName;
  private String fatherName;
  private boolean isActive;

}
