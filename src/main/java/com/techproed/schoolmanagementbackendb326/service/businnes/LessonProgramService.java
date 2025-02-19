package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.repository.businnes.LessonProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

  private final LessonProgramRepository lessonProgramRepository;

}
