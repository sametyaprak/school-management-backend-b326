package com.techproed.schoolmanagementbackendb326.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techproed.schoolmanagementbackendb326.entity.enums.Day;
import java.time.LocalTime;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonProgramRequest {

  @NotNull(message="Please enter day")
  private Day day;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
  @NotNull(message="Please enter start time")
  private LocalTime startTime;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
  @NotNull(message="Please enter stop time")
  private LocalTime stopTime;

  @NotNull(message="Please select lesson")
  @Size(min=1, message ="Lesson must not be empty")
  private Set<Long> lessonIdList;

  @NotNull(message="Please enter education term")
  private Long educationTermId;

}
