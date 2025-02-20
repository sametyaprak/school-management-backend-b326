package com.techproed.schoolmanagementbackendb326.controller.businnes;

import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonProgramRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonProgramResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.businnes.LessonProgramService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lessonPrograms")
@RequiredArgsConstructor
public class LessonProgramController {

  private final LessonProgramService lessonProgramService;


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PostMapping("/save")
  public ResponseMessage<LessonProgramResponse>saveLessonProgram(
      @RequestBody @Valid LessonProgramRequest lessonProgramRequest) {
    return lessonProgramService.saveLessonProgram(lessonProgramRequest);
  }

  //TODO KEMAL
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
  @GetMapping("/getAll")
  public List<LessonProgramResponse> getAllLessonPrograms(){
    //return lessonProgramService.getAllLessonPrograms();
    return null;
  }

  //TODO LEVEN
  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
  @GetMapping("/getLessonProgram/{id}")
  public LessonProgramResponse getLessonProgramById(@PathVariable Long id){
    //return lessonProgramService.findById(id);
    return null;
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
  @GetMapping("/getAllUnassigned")
  public List<LessonProgramResponse>getAllUnassignedLessonPrograms(){
    return lessonProgramService.getAllUnassigned();
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean','Teacher','Student')")
  @GetMapping("/getAllAssigned")
  public List<LessonProgramResponse> getAllAssignedLessonPrograms(){
    return lessonProgramService.getAllAssigned();
  }

  //TODO ESRA
  @PreAuthorize("hasAnyAuthority('Admin','Teacher')")
  @DeleteMapping("/delete/{id}")
  public ResponseMessage deleteLessonProgramById(@PathVariable Long id){
    //return lessonProgramService.deleteLessonProgramById(id);
    return null;
  }






}
