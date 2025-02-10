package com.techproed.schoolmanagementbackendb326.payload.response.abstracts;

import com.techproed.schoolmanagementbackendb326.entity.enums.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserResponse {

  private Long id;
  private String username;
  private String name;
  private String surname;
  private LocalDate birthDay;
  private String ssn;
  private String birthPlace;
  private String phoneNumber;
  private String email;
  private Gender gender;
  private String userRole;





}
