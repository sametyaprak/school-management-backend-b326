package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.exception.ConflictException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.LessonMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.businnes.LessonRepository;
import javax.validation.Valid;

import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService {

  private final LessonRepository lessonRepository;
  private final LessonMapper lessonMapper;
   private final PageableHelper pageableHelper;

  public ResponseMessage<LessonResponse> saveLesson(@Valid LessonRequest lessonRequest) {
  //validate - lesson name must be unique
  isLessonExistByName(lessonRequest.getLessonName());
  //map DTO to Entity
    Lesson lesson = lessonMapper.mapLessonRequestToLesson(lessonRequest);
    Lesson savedLesson = lessonRepository.save(lesson);

    return ResponseMessage.<LessonResponse>builder()
        .returnBody(lessonMapper.mapLessonToLessonResponse(savedLesson))
        .httpStatus(HttpStatus.CREATED)
        .message(SuccessMessages.LESSON_SAVE)
        .build();
  }








  private void isLessonExistByName(String lessonName) {
    if(lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName).isPresent()) {
      throw new ConflictException(String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE,lessonName));
    }
  }


  public Page<LessonResponse> getLessonByPage(int page, int size, String sort, String type) {


    Pageable pageable = pageableHelper.getPageable(page, size, sort, type);

    Page<Lesson> lessons = lessonRepository.findAll(pageable);
// use mapper
    return lessons.map(lessonMapper::mapLessonToLessonResponse);


  }
}
