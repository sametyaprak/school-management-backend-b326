package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.exception.ConflictException;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.LessonMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.businnes.LessonRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import javax.validation.Valid;
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

  public ResponseMessage<LessonResponse> deleteLesson(Long lessonId) {

    return ResponseMessage.<LessonResponse>builder()
        .returnBody(lessonMapper.mapLessonToLessonResponse(deleteLessonById(lessonId)))
        .httpStatus(HttpStatus.OK)
        .message(SuccessMessages.LESSON_DELETE)
        .build();
  }

  public ResponseMessage<LessonResponse> findLessonByName(String lessonName) {
    return ResponseMessage.<LessonResponse>builder()
        .message(SuccessMessages.LESSON_FOUND)
        .returnBody(lessonMapper.mapLessonToLessonResponse(getLessonByName(lessonName)))
        .httpStatus(HttpStatus.OK)
        .build();
  }


  private void isLessonExistByName(String lessonName) {
    if (lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName).isPresent()) {
      throw new ConflictException(
          String.format(ErrorMessages.ALREADY_CREATED_LESSON_MESSAGE, lessonName));
    }
  }

  private Lesson getLessonByName(String lessonName) {
    return lessonRepository.findByLessonNameEqualsIgnoreCase(lessonName)
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ErrorMessages.NOT_FOUND_LESSON_IN_LIST, lessonName)));
  }


  public Page<LessonResponse> getLessonByPage(int page, int size, String sort, String type) {
    Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
    Page<Lesson> lessons = lessonRepository.findAll(pageable);
    return lessons.map(lessonMapper::mapLessonToLessonResponse);
  }

  private Lesson deleteLessonById(Long lessonId) {
    Lesson lesson = lessonRepository.findById(lessonId)
        .orElseThrow(() -> new ResourceNotFoundException(
            String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, lessonId)));
    lessonRepository.delete(lesson);
    return lesson;
  }

  public Lesson isLessonExistById(Long lessonId) {
    return lessonRepository.findById(lessonId)
        .orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_LESSON_MESSAGE, lessonId)));
  }


  public LessonResponse updateLesson(@Valid LessonRequest lessonRequest, Long lessonId) {
    //validate if exist
    Lesson lessonFromDb = isLessonExistById(lessonId);
    //check if user changes the name
    if(!lessonRequest.getLessonName().equals(lessonFromDb.getLessonName())) {
      //then check DB in case of conflict
      isLessonExistByName(lessonRequest.getLessonName());
    }
    Lesson lessonToUpdate = lessonMapper.mapLessonRequestToLesson(lessonRequest);
    lessonToUpdate.setId(lessonId);
    Lesson savedLesson = lessonRepository.save(lessonToUpdate);
    return lessonMapper.mapLessonToLessonResponse(savedLesson);
  }
}
