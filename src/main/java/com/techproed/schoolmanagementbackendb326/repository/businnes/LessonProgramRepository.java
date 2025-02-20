package com.techproed.schoolmanagementbackendb326.repository.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonProgramRepository extends JpaRepository<LessonProgram, Long> {

    List<LessonProgram>findByUsers_IdNull();

    List<LessonProgram>findByUsers_IdNotNull();


}
