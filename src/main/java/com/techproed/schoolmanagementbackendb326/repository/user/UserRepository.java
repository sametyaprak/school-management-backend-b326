package com.techproed.schoolmanagementbackendb326.repository.user;

import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import java.util.List;

import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  boolean existsBySsn(String ssn);

  boolean existsByPhoneNumber(String phoneNumber);

  //Page<User>findUserByUserRole(UserRole userRole, Pageable pageable);

  @Query("select u from User u where u.userRole.roleName = :roleName")
  Page<User>findUserByUserRoleQuery(String roleName, Pageable pageable);

  User findByUsername(String username);

  @Query("select (count (u) > 0) from User u where u.userRole.roleType = 'STUDENT' ")
  boolean findStudent();

  @Query("select max (u.studentNumber) from User u")
  int getMaxStudentNumber();

  List<User>findByAdvisorTeacherId(Long advisorTeacherId);


  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.advisorTeacherId = NULL WHERE u.advisorTeacherId = :teacherId")
  void removeAdvisorFromStudents(@Param("teacherId") Long teacherId);

  Page<User> findAllByUserRole(RoleType roleType, Pageable pageable);

  @Query("select u from User u where u.id IN :userIdList")
  List<User>findByUserIdList(List<Long> userIdList);
}
