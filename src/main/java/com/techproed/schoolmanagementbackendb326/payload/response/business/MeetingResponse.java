package com.techproed.schoolmanagementbackendb326.payload.response.business;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MeetingResponse {

  private Long id;
  private String description;
  private LocalDate date;
  private LocalTime startTime;
  private LocalTime stopTime;
  private Long advisorTeacherId;
  private List<User>students;

}
