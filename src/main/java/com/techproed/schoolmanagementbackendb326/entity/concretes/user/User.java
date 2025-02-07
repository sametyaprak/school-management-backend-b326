package com.techproed.schoolmanagementbackendb326.entity.concretes.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.sun.tools.javac.jvm.Gen;
import com.techproed.schoolmanagementbackendb326.entity.enums.Gender;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="t_user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(unique=true)
  private String username;
  @Column(unique=true)
  private String ssn;
  private String name;
  private String surname;
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
  private LocalDate birthday;
  private String birthplace;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  @Column(unique=true)
  private String phoneNumber;
  @Column(unique=true)
  private String email;
  private Boolean buildIn;
  private String motherName;
  private String fatherName;
  private int studentNumber;
  private boolean isActive;
  private Boolean isAdvisor;
  private Long advisorTeacherId;
  private Gender gender;

  @OneToOne
  @JsonProperty(access = Access.WRITE_ONLY)
  private UserRole userRole;






}
