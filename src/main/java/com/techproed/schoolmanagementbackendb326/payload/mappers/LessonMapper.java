package com.techproed.schoolmanagementbackendb326.payload.mappers;


import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonResponse;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {


  public Lesson mapLessonRequestToLesson(LessonRequest lessonRequest) {
    return Lesson.builder()
        .lessonName(lessonRequest.getLessonName())
        .creditScore(lessonRequest.getCreditScore())
        .isCompulsory(lessonRequest.getIsCompulsory())
        .build();
  }


  public LessonResponse mapLessonToLessonResponse(Lesson lesson) {
    return LessonResponse.builder()
        .lessonId(lesson.getId())
        .lessonName(lesson.getLessonName())
        .creditScore(lesson.getCreditScore())
        .isCompulsory(lesson.getIsCompulsory())
        .build();
  }



}
