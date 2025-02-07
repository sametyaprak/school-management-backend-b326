package com.techproed.schoolmanagementbackendb326.entity.concretes.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.Note;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Integer absentee;

  private Double midtermExam;

  private Double finalExam;

  private String infoNote;

  private Double examAverage;

  @Enumerated(EnumType.STRING)
  private Note letterGrade;

  @ManyToOne
  @JsonIgnore
  private User teacher;

  @ManyToOne
  @JsonIgnore
  private User student;

  @ManyToOne
  private Lesson lesson;

  @OneToOne
  private EducationTerm educationTerm;


}
