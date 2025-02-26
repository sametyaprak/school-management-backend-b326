package com.techproed.schoolmanagementbackendb326.payload.mappers;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.StudentInfo;
import com.techproed.schoolmanagementbackendb326.entity.enums.Note;
import com.techproed.schoolmanagementbackendb326.payload.request.business.StudentInfoRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.user.StudentRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.StudentInfoResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerResponse;

@Component
@Data
@RequiredArgsConstructor
public class StudentInfoMapper {

  private final UserMapper userMapper;

  public StudentInfo mapStudentInfoRequestToStudentInfo(
      StudentInfoRequest studentInfoRequest,
      Note note, Double average) {
    return StudentInfo.builder()
        .infoNote(studentInfoRequest.getInfoNote())
        .absentee(studentInfoRequest.getAbsentee())
        .midtermExam(studentInfoRequest.getMidtermExam())
        .finalExam(studentInfoRequest.getFinalExam())
        .examAverage(average)
        .letterGrade(note)
        .build();
  }

  public StudentInfoResponse mapStudentInfoToStudentInfoResponse(StudentInfo studentInfo) {
    return StudentInfoResponse.builder()
        .lessonName(studentInfo.getLesson().getLessonName())
        .creditScore(studentInfo.getLesson().getCreditScore())
        .isCompulsory(studentInfo.getLesson().getIsCompulsory())
        .educationTerm(studentInfo.getEducationTerm().getTerm())
        .id(studentInfo.getId())
        .absentee(studentInfo.getAbsentee())
        .midtermExam(studentInfo.getMidtermExam())
        .finalExam(studentInfo.getFinalExam())
        .infoNote(studentInfo.getInfoNote())
        .note(studentInfo.getLetterGrade())
        .average(studentInfo.getExamAverage())
        .studentResponse(userMapper.mapUserToStudentResponse(studentInfo.getStudent()))
        .build();
  }


}
