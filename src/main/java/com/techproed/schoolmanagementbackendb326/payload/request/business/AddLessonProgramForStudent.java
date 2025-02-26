package com.techproed.schoolmanagementbackendb326.payload.request.business;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLessonProgramForStudent {


  @NotNull(message = "Please select lesson program")
  @Size(min = 1,message = "Lessons must not be empty")
  private List<Long> lessonProgramId;

}
