package com.techproed.schoolmanagementbackendb326;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.UserRole;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.repository.user.UserRoleRepository;
import com.techproed.schoolmanagementbackendb326.service.user.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolManagementBackendB326Application implements CommandLineRunner {

  private final UserRoleService userRoleService;
  private final UserRoleRepository userRoleRepository;

  public SchoolManagementBackendB326Application(UserRoleService userRoleService,
      UserRoleRepository userRoleRepository) {
    this.userRoleService = userRoleService;
    this.userRoleRepository = userRoleRepository;
  }

  public static void main(String[] args) {
    SpringApplication.run(SchoolManagementBackendB326Application.class, args);
  }


  @Override
  public void run(String... args) throws Exception {
    if(userRoleService.getAllUserRoles().isEmpty()){
      //admin
      UserRole admin = new UserRole();
      admin.setRoleType(RoleType.ADMIN);
      admin.setRoleName(RoleType.ADMIN.getName());
      userRoleRepository.save(admin);
      //dean
      UserRole dean = new UserRole();
      dean.setRoleType(RoleType.MANAGER);
      dean.setRoleName(RoleType.MANAGER.getName());
      userRoleRepository.save(dean);
      //vice-dean
      UserRole viceDean = new UserRole();
      viceDean.setRoleType(RoleType.ASSISTANT_MANAGER);
      viceDean.setRoleName(RoleType.ASSISTANT_MANAGER.getName());
      userRoleRepository.save(viceDean);
      //student
      UserRole student = new UserRole();
      student.setRoleType(RoleType.STUDENT);
      student.setRoleName(RoleType.STUDENT.getName());
      userRoleRepository.save(student);
      //teacher
      UserRole teacher = new UserRole();
      teacher.setRoleType(RoleType.TEACHER);
      teacher.setRoleName(RoleType.TEACHER.getName());
      userRoleRepository.save(teacher);
    }
  }
}
