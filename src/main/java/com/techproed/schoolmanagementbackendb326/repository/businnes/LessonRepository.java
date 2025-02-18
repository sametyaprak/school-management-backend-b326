package com.techproed.schoolmanagementbackendb326.repository.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

  //@Query("select l from Lesson l where l.lessonName = :lessonName")
  Optional<Lesson> findByLessonNameEqualsIgnoreCase(String lessonName);


}
