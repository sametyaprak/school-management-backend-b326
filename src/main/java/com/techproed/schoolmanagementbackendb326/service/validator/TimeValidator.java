package com.techproed.schoolmanagementbackendb326.service.validator;

import com.techproed.schoolmanagementbackendb326.exception.BadRequestException;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

@Component
public class TimeValidator {


  //validate if start time is before stop time
  public void checkStartIsBeforeStop(LocalTime start, LocalTime stop) {
    if (start.isAfter(stop) || start.equals(stop)){
      throw new BadRequestException(ErrorMessages.TIME_NOT_VALID_MESSAGE);
    }
  }

}
