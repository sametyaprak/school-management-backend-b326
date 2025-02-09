package com.techproed.schoolmanagementbackendb326.payload.request.abstracts;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseUserRequest extends AbstractUserRequest{

  @NotNull(message = "Please enter your password")
  @Size(min = 8,max = 20,message = "your password shall be 8-20 characters")
  private String password;

  private Boolean buildIn = false;

}
