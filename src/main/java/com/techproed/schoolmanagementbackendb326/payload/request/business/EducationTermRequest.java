package com.techproed.schoolmanagementbackendb326.payload.request.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techproed.schoolmanagementbackendb326.entity.enums.Term;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationTermRequest {
  @NotNull(message = "Education term must not be empty")
  private Term term;

  @NotNull(message = "Start date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @NotNull(message = "End date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @NotNull(message = "Last registration date must not be empty")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate lastRegistrationDate;

}
