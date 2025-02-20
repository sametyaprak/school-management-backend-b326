package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import com.techproed.schoolmanagementbackendb326.payload.mappers.LessonProgramMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonProgramRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonProgramResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.businnes.LessonProgramRepository;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonProgramService {

  private final LessonProgramRepository lessonProgramRepository;
  private final LessonService lessonService;
  private final EducationTermService educationTermService;
  private final TimeValidator timeValidator;
  private final LessonProgramMapper lessonProgramMapper;

  public ResponseMessage<LessonProgramResponse> saveLessonProgram(
      @Valid LessonProgramRequest lessonProgramRequest) {
    //get lessons from DB
    List<Lesson> lessons = lessonService.getAllByIdSet(lessonProgramRequest.getLessonIdList());
    //get education term from DB
    EducationTerm educationTerm = educationTermService.isEducationTermExist(lessonProgramRequest.getEducationTermId());
    //validate start + end time
    timeValidator.checkStartIsBeforeStop(
        lessonProgramRequest.getStartTime(),lessonProgramRequest.getStopTime());
    //mapping
    LessonProgram lessonProgramToSave = lessonProgramMapper.mapLessonProgramRequestToLessonProgram(
        lessonProgramRequest,lessons,educationTerm);
    LessonProgram savedLessonProgram = lessonProgramRepository.save(lessonProgramToSave);
    return ResponseMessage.<LessonProgramResponse>
        builder()
        .returnBody(lessonProgramMapper.mapLessonProgramToLessonProgramResponse(savedLessonProgram))
        .httpStatus(HttpStatus.CREATED)
        .message(SuccessMessages.LESSON_PROGRAM_SAVE)
        .build();
  }
}
