package com.techproed.schoolmanagementbackendb326.entity.concretes.business;

import com.techproed.schoolmanagementbackendb326.entity.enums.Day;
import java.time.LocalTime;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LessonProgram {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Day day;

  private LocalTime startTime;

  private LocalTime stopTime;

  @ManyToMany
  @JoinTable(
      name = "lesson_program_lesson",
      joinColumns = @JoinColumn(name = "lessonprogram_id"),
      inverseJoinColumns = @JoinColumn(name = "lesson_id")
  )
  private Set<Lesson>lessons;

  @ManyToOne
  private EducationTerm educationTerm;

}
