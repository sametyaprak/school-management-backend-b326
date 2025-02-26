package com.techproed.schoolmanagementbackendb326.controller.businnes;

import com.techproed.schoolmanagementbackendb326.payload.request.business.MeetingRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.MeetingResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.businnes.MeetingService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/meeting")
@RestController
public class MeetingController {

  public final MeetingService meetingService;


  //TODO bu zamana kadar return olarak verilen response message icindeki
  //response status calismiyor. Bunu asagidaki annotation ile giderebilirirz.
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAnyAuthority('Teacher')")
  @PostMapping("/save")
  public ResponseMessage<MeetingResponse>saveMeeting(
      HttpServletRequest httpServletRequest,
      @RequestBody @Valid MeetingRequest meetingRequest){
    return meetingService.save(httpServletRequest,meetingRequest);
  }

  @PreAuthorize("hasAnyAuthority('Teacher')")
  @PutMapping("/update/{meetingId}")
  public ResponseMessage<MeetingResponse>updateMeeting(
      @RequestBody @Valid MeetingRequest meetingRequest,
      @PathVariable Long meetingId,
      HttpServletRequest httpServletRequest){
    return meetingService.update(meetingRequest,meetingId,httpServletRequest);
  }


  //TODO furkan
  @PreAuthorize("hasAnyAuthority('Admin','Teacher')")
  @DeleteMapping("/delete/{meetingId}")
  public ResponseMessage deleteById(@PathVariable Long meetingId){
    //return meetingService.deleteById(meetingId);
    return null;
  }

  //TODO neslihan
  @PreAuthorize("hasAnyAuthority('Teacher')")
  @GetMapping("/getAllByPageTeacher")
  public Page<MeetingResponse> getAllByPageTeacher(
      HttpServletRequest httpServletRequest,
      @RequestParam(value = "page",defaultValue = "0") int page,
      @RequestParam(value = "size",defaultValue = "10") int size){
    //return meetingService.getAllByPageTeacher(page,size,httpServletRequest);
    return null;
  }

  //TODO edip
  @PreAuthorize("hasAnyAuthority('Teacher','Student')")
  @GetMapping("/getAll")
  public List<MeetingResponse> getAllMeetings(HttpServletRequest httpServletRequest){
    //return meetingService.getAll(httpServletRequest);
    return null;
  }

  //TODO ismail
  @PreAuthorize("hasAnyAuthority('Admin')")
  @GetMapping("/getAllByPage")
  public Page<MeetingResponse>getAllByPage(
      @RequestParam(value = "page") int page,
      @RequestParam(value = "size") int size){
    //return meetingService.getAllByPage(page, size);
    return null;
  }





}
