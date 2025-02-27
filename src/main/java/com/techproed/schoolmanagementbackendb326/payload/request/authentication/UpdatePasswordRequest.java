package com.techproed.schoolmanagementbackendb326.payload.request.authentication;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

  @NotBlank(message = "Please provide old password")
  private String oldPassword;

  @NotBlank(message = "Please provide new password")
  private String newPassword;

}
