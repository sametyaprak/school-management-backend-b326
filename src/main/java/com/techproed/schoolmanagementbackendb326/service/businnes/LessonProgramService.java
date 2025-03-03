package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.LessonProgram;
import com.techproed.schoolmanagementbackendb326.exception.BadRequestException;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.LessonProgramMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonProgramRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonProgramResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.businnes.LessonProgramRepository;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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


  public List<LessonProgramResponse> getAllUnassigned() {
    return lessonProgramRepository.findByUsers_IdNull()
        .stream()
        .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
        .collect(Collectors.toList());
  }

  public List<LessonProgramResponse> getAllAssigned() {
    return lessonProgramRepository.findByUsers_IdNotNull()
        .stream()
        .map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse)
        .collect(Collectors.toList());
  }


  // Check if it exists in the database
  public LessonProgram existById(Long id) {
    return lessonProgramRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LESSON_PROGRAM_MESSAGE));
  }

  public ResponseMessage deleteLessonProgramById(Long id) {
    existById(id);
    lessonProgramRepository.deleteById(id);
    return ResponseMessage.builder()
            .message(SuccessMessages.LESSON_PROGRAM_DELETE)
            .httpStatus(HttpStatus.OK)
            .build();
  }


  public List<LessonProgram>getLessonProgramById(List<Long>lessonIdList) {
    lessonIdList.forEach(this::existById);
    List<LessonProgram> lessonProgramList = lessonProgramRepository.findAllById(lessonIdList);
    if(lessonProgramList.isEmpty()){
      throw new BadRequestException(ErrorMessages.NOT_FOUND_LESSON_PROGRAM_MESSAGE_WITHOUT_ID_INFO);
    }
    return lessonProgramList;
  }



  public List<LessonProgramResponse> getAllLessonPrograms() {
    List<LessonProgram> allLessonPrograms = lessonProgramRepository.findAll();
    return allLessonPrograms.stream().map(lessonProgramMapper::mapLessonProgramToLessonProgramResponse).collect(Collectors.toList());
  }


  public LessonProgram isLessonProgramExist(Long id) {
    return lessonProgramRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.NOT_FOUND_LESSON_IN_LIST));
  }

    public LessonProgramResponse findById(Long id) {

      LessonProgram lessonProgram = isLessonProgramExist(id);
      return lessonProgramMapper.mapLessonProgramToLessonProgramResponse(lessonProgram);

    }
}
