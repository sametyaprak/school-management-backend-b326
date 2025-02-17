package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.exception.BadRequestException;
import com.techproed.schoolmanagementbackendb326.exception.ConflictException;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.EducationTermMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.EducationTermRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.EducationTermResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.businnes.EducationTermRepository;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationTermService {

  private final EducationTermRepository educationTermRepository;
  private final EducationTermMapper educationTermMapper;

  public ResponseMessage<EducationTermResponse> save(
      @Valid EducationTermRequest educationTermRequest) {
    //validation
    validateEducationTermDates(educationTermRequest);
    //write mappers DTO->Entity + Entity->DTO
    EducationTerm educationTerm = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
    EducationTerm savedEducationTerm = educationTermRepository.save(educationTerm);
    return ResponseMessage.<EducationTermResponse>builder()
        .message(SuccessMessages.EDUCATION_TERM_SAVE)
        .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(savedEducationTerm))
        .httpStatus(HttpStatus.CREATED)
        .build();
  }


  private void validateEducationTermDates(EducationTermRequest educationTermRequest) {
    //validate request by reg/start/stop
    validateEducationTermDatesForRequest(educationTermRequest);
    //only one education term can exist in a year
    if(educationTermRepository.existByTermAndYear(
        educationTermRequest.getTerm(),
        educationTermRequest.getStartDate().getYear())){
      throw new ConflictException(ErrorMessages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
    }
    //validate not to have any conflict with other education terms
    educationTermRepository.findByYear(educationTermRequest.getStartDate().getYear())
        .forEach(educationTerm -> {
          if(!educationTerm.getStartDate().isAfter(educationTermRequest.getEndDate())
          || educationTerm.getEndDate().isBefore(educationTermRequest.getStartDate())){
            throw new BadRequestException(ErrorMessages.EDUCATION_TERM_CONFLICT_MESSAGE);
          }
        });
  }


  private void validateEducationTermDatesForRequest(EducationTermRequest educationTermRequest) {
    //reg<start
    if(educationTermRequest.getLastRegistrationDate().isAfter(educationTermRequest.getStartDate())){
      throw new ConflictException(ErrorMessages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
    }
    //end>start
    if(educationTermRequest.getEndDate().isBefore(educationTermRequest.getStartDate())){
      throw new ConflictException(ErrorMessages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
    }
  }

  public ResponseMessage<EducationTermResponse> updateEducationTerm(
      @Valid EducationTermRequest educationTermRequest, Long educationTermId) {
    //check if education term exist
    isEducationTermExist(educationTermId);
    //validate dates
    validateEducationTermDatesForRequest(educationTermRequest);
    //mapping
    EducationTerm term = educationTermMapper.mapEducationTermRequestToEducationTerm(educationTermRequest);
    term.setId(educationTermId);
    //return by mapping it to DTO
    return ResponseMessage.<EducationTermResponse>builder()
        .message(SuccessMessages.EDUCATION_TERM_UPDATE)
        .returnBody(educationTermMapper.mapEducationTermToEducationTermResponse(educationTermRepository.save(term)))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public EducationTerm isEducationTermExist(Long educationTermId) {
    return educationTermRepository.findById(educationTermId)
        .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.EDUCATION_TERM_NOT_FOUND_MESSAGE));
  }


}
