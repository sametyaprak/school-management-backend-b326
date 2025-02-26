package com.techproed.schoolmanagementbackendb326.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeetingRequest {


  @NotNull(message = "Please enter description")
  @Size(min=2, max= 250, message = "Description should be at least 2 chars")
  @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Description must consist of the characters .")
  private String description;

  @NotNull(message = "Please enter day")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @Future(message = "Meeting can not be in the past")
  private LocalDate date;

  @NotNull(message = "Please enter start time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
  private LocalTime startTime;

  @NotNull(message = "Please enter stop time")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "US")
  private LocalTime stopTime;

  @NotNull(message = "Please select students")
  private List<Long> studentIds;

}
