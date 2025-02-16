package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.exception.BadRequestException;
import com.techproed.schoolmanagementbackendb326.exception.ConflictException;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.EducationTermRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.EducationTermResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.businnes.EducationTermRepository;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducationTermService {

  private final EducationTermRepository educationTermRepository;

  public ResponseMessage<EducationTermResponse> save(
      @Valid EducationTermRequest educationTermRequest) {
    //validation
    validateEducationTermDates(educationTermRequest);
    //TODO
    //write mappers DTO->Entity + Entity->DTO
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




}
