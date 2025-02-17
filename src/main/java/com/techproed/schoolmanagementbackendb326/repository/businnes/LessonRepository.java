package com.techproed.schoolmanagementbackendb326.repository.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
