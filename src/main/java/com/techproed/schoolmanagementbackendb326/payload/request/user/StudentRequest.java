package com.techproed.schoolmanagementbackendb326.payload.request.user;

import com.techproed.schoolmanagementbackendb326.payload.request.abstracts.BaseUserRequest;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class StudentRequest extends BaseUserRequest {

  @NotNull(message = "Please enter mother name")
  @Size(min = 2, max = 16, message = "Your mother name should be at least 2 chars")
  @Pattern(regexp="\\A(?!\\s*\\Z).+",message="Your mother name must consist of the characters a-z and 0-9.")
  private String motherName;

  @NotNull(message = "Please enter father name")
  @Size(min = 2, max = 16, message = "Your father name should be at least 2 chars")
  @Pattern(regexp="\\A(?!\\s*\\Z).+",message="Your father name must consist of the characters a-z and 0-9.")
  private String fatherName;

  @NotNull(message = "Please select advisor teacher")
  private Long advisorTeacherId;

}
