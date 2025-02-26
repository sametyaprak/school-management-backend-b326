package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Meet;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.payload.mappers.MeetingMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.MeetingRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.MeetingResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.businnes.MeetingRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.MeetingHelper;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import com.techproed.schoolmanagementbackendb326.service.validator.TimeValidator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeetingService {

  private final MeetingRepository meetingRepository;
  private final TimeValidator timeValidator;
  private final MeetingMapper meetingMapper;
  private final PageableHelper pageableHelper;
  private final MethodHelper methodHelper;
  private final MeetingHelper meetingHelper;


  public ResponseMessage<MeetingResponse> save(HttpServletRequest httpServletRequest,
      MeetingRequest meetingRequest) {
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    timeValidator.checkStartIsBeforeStop(
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    meetingHelper.checkMeetingConflicts(
        meetingRequest.getStudentIds(),
        teacher.getId(),
        meetingRequest.getDate(),
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    List<User>students = methodHelper.getUserList(meetingRequest.getStudentIds());
    Meet meetToSave = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
    meetToSave.setStudentList(students);
    meetToSave.setAdvisoryTeacher(teacher);
    Meet savedMeeting = meetingRepository.save(meetToSave);
    return ResponseMessage.<MeetingResponse>builder()
        .message(SuccessMessages.MEET_SAVE)
        .returnBody(meetingMapper.mapMeetingToMeetingResponse(savedMeeting))
        .httpStatus(HttpStatus.CREATED)
        .build();
  }

  public ResponseMessage<MeetingResponse> update(@Valid MeetingRequest meetingRequest,
      Long meetingId, HttpServletRequest httpServletRequest) {
    Meet existingMeeting = meetingHelper.isMeetingExistById(meetingId);
    //validate is logged in teacher owner of this meeting
    meetingHelper.isMeetingMatchedWithTeacher(existingMeeting, httpServletRequest);
    timeValidator.checkStartIsBeforeStop(
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    meetingHelper.checkMeetingConflicts(
        meetingRequest.getStudentIds(),
        existingMeeting.getAdvisoryTeacher().getId(),
        meetingRequest.getDate(),
        meetingRequest.getStartTime(),
        meetingRequest.getStopTime());
    List<User>students = methodHelper.getUserList(meetingRequest.getStudentIds());
    Meet meetingToUpdate = meetingMapper.mapMeetingRequestToMeet(meetingRequest);
    meetingToUpdate.setStudentList(students);
    meetingToUpdate.setAdvisoryTeacher(existingMeeting.getAdvisoryTeacher());
    meetingToUpdate.setId(existingMeeting.getId());
    Meet updatedMeeting = meetingRepository.save(meetingToUpdate);
    return ResponseMessage.<MeetingResponse>builder()
        .message(SuccessMessages.MEET_UPDATE)
        .returnBody(meetingMapper.mapMeetingToMeetingResponse(updatedMeeting))
        .build();
  }
}
