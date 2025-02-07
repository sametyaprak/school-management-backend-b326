package com.techproed.schoolmanagementbackendb326.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.techproed.schoolmanagementbackendb326.entity.enums.Term;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EducationTerm {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  private Term term;

  @Column(name = "start_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @Column(name = "end_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @Column(name = "last_registration_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
  private LocalDate lastRegistrationDate;

  @OneToMany(mappedBy = "educationTerm",cascade = CascadeType.ALL)
  private List<LessonProgram>lessonProgram;





}
