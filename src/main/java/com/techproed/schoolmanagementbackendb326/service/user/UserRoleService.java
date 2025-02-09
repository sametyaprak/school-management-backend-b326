package com.techproed.schoolmanagementbackendb326.service.user;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.UserRole;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

  private final UserRoleRepository userRoleRepository;

  public UserRole getUserRole(RoleType roleType){
    return userRoleRepository.findByUserRoleType(roleType)
        .orElseThrow(() -> new RuntimeException("User Role Not Found"));
  }

}
