package com.techproed.schoolmanagementbackendb326.payload.mappers;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Meet;
import com.techproed.schoolmanagementbackendb326.payload.request.business.MeetingRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.MeetingResponse;
import org.springframework.stereotype.Component;

@Component
public class MeetingMapper {

  public Meet mapMeetingRequestToMeet(MeetingRequest meetingRequest){
    return Meet.builder()
        .date(meetingRequest.getDate())
        .startTime(meetingRequest.getStartTime())
        .stopTime(meetingRequest.getStopTime())
        .description(meetingRequest.getDescription())
        .build();
  }

  public MeetingResponse mapMeetingToMeetingResponse(Meet meeting){
    return MeetingResponse.builder()
        .id(meeting.getId())
        .date(meeting.getDate())
        .startTime(meeting.getStartTime())
        .stopTime(meeting.getStopTime())
        .description(meeting.getDescription())
        .advisorTeacherId(meeting.getAdvisoryTeacher().getId())
        .students(meeting.getStudentList())
        .build();
  }







}
