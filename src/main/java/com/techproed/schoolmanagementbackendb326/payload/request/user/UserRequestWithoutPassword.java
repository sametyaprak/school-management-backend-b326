package com.techproed.schoolmanagementbackendb326.payload.request.user;

import com.techproed.schoolmanagementbackendb326.payload.request.abstracts.AbstractUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class UserRequestWithoutPassword extends AbstractUserRequest {

}
