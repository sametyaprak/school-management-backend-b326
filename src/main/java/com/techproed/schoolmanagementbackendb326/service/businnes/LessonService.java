package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import javax.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class LessonService {

  public ResponseMessage<LessonResponse> saveLesson(@Valid LessonRequest lessonRequest) {
  //validate - lesson name must be unique


  }




  private void isLessonExistByName(String lessonName) {

  }


}
