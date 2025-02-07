package com.techproed.schoolmanagementbackendb326.entity.enums;

import lombok.Getter;

@Getter
public enum RoleType {

  ADMIN("Admin"),
  TEACHER("Teacher"),
  STUDENT("Student"),
  MANAGER("Manager"),
  ASSISTANT_MANAGER("ViceDean");

  public final String name;

  RoleType(String name) {
    this.name = name;
  }
}
