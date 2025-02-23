package com.techproed.schoolmanagementbackendb326.payload.request.user;

import com.techproed.schoolmanagementbackendb326.payload.request.abstracts.BaseUserRequest;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TeacherRequest extends BaseUserRequest {

  @NotNull(message = "Please select lesson program")
  private List<Long> lessonProgramList;

  @NotNull(message = "Please select isAdvisory teacher")
  private Boolean isAdvisoryTeacher;

}
