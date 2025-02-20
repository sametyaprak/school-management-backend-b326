package com.techproed.schoolmanagementbackendb326.controller.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.payload.request.business.LessonRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.LessonResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.businnes.LessonService;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
public class LessonController {

  private final LessonService lessonService;

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PostMapping("/save")
  public ResponseMessage<LessonResponse>saveLesson(
      @RequestBody @Valid LessonRequest lessonRequest) {
    return lessonService.saveLesson(lessonRequest);
  }

  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @PutMapping("/update/{lessonId}")
  public ResponseEntity<LessonResponse>updateLessonById(
      @PathVariable Long lessonId,
      @RequestBody @Valid LessonRequest lessonRequest){
    return ResponseEntity.ok(lessonService.updateLesson(lessonRequest,lessonId));
  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @DeleteMapping("/delete/{lessonId}")
  public ResponseMessage deleteLesson(@PathVariable Long lessonId) {
    return lessonService.deleteLesson(lessonId);
  }



  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getLessonByName/{lessonName}")
  public ResponseMessage<LessonResponse>getLessonByName(
          @PathVariable String lessonName){
    return lessonService.findLessonByName(lessonName);

  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getLessonByPage")
  public Page<LessonResponse> findLessonByPage(
      @RequestParam(value = "page",defaultValue = "0") int page,
      @RequestParam(value = "size",defaultValue = "10") int size,
      @RequestParam(value = "sort",defaultValue = "lessonName") String sort,
      @RequestParam(value = "type",defaultValue = "desc") String type
  ){
    return lessonService.getLessonByPage(page,size,sort,type);
  }


  @PreAuthorize("hasAnyAuthority('Admin','Dean','ViceDean')")
  @GetMapping("/getAllByIdSet")
  public List<Lesson> getAllByIdSet(@RequestParam(name = "lessonId") List<Long> idSet){
    return lessonService.getAllByIdSet(idSet);
  }





}
