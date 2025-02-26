package com.techproed.schoolmanagementbackendb326.service.helper;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Meet;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.exception.BadRequestException;
import com.techproed.schoolmanagementbackendb326.exception.ConflictException;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.repository.businnes.MeetingRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MeetingHelper {

  private final MethodHelper methodHelper;
  private final MeetingRepository meetingRepository;

  // Ö1 > m1 , m4
  // Ö2 > m1 , m3
  // Ö3 > m2 , m5 , m6
  // m1, m2, m4, m5, m6
  // Ögretmen1 > m5, m2
  // m1, m2, m4, m5, m6 ---- MONDAY , 08:00-11:00

  public void checkMeetingConflicts(
      List<Long>studentIdList, Long teacherId, LocalDate meetingDate,
      LocalTime startTime, LocalTime stopTime){
    Set<Meet> existingMeetings = new HashSet<>();
    for (Long id:studentIdList){
      //check a student really exist + is a student
      methodHelper.checkUserRole(methodHelper.isUserExist(id), RoleType.STUDENT);
      //add all student's meetings to collection
      existingMeetings.addAll(meetingRepository.findByStudentList_IdEquals(id));
    }
    //add all teacher meetings to collection
    existingMeetings.addAll(meetingRepository.getByAdvisoryTeacher_IdEquals(teacherId));
    //then compare each of them with DTO
    for (Meet meet:existingMeetings){
      if (existingMeetings.size()==1){
        continue;
      } else {
        LocalTime existingStartTime = meet.getStartTime();
        LocalTime existingStopTime = meet.getStopTime();
        if(meet.getDate().equals(meetingDate) && (
            (startTime.isAfter(existingStartTime) && startTime.isBefore(existingStopTime)) ||
                (stopTime.isAfter(existingStartTime) && stopTime.isBefore(existingStopTime)) ||
                (startTime.isBefore(existingStartTime) && stopTime.isAfter(existingStopTime)) ||
                (startTime.equals(existingStartTime) || stopTime.equals(existingStopTime))
        )) {
          throw new ConflictException(ErrorMessages.MEET_HOURS_CONFLICT);
        }
      }
    }
  }

  public Meet isMeetingExistById(Long id){
    return meetingRepository.findById(id).orElseThrow(
        ()-> new ResourceNotFoundException(String.format(ErrorMessages.MEET_NOT_FOUND_MESSAGE,id)));
  }

  public void isMeetingMatchedWithTeacher(Meet meeting, HttpServletRequest httpServletRequest){
    String username = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(username);
    methodHelper.checkIsAdvisor(teacher);
    if(!meeting.getAdvisoryTeacher().getId().equals(teacher.getId())){
      throw new BadRequestException(ErrorMessages.NOT_PERMITTED_METHOD_MESSAGE);
    }
  }


}
