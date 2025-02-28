package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.StudentInfo;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.Note;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.StudentInfoMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.StudentInfoRequest;
import com.techproed.schoolmanagementbackendb326.payload.request.business.StudentInfoUpdateRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.business.StudentInfoResponse;
import com.techproed.schoolmanagementbackendb326.repository.businnes.StudentInfoRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.helper.StudentInfoHelper;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentInfoService {

  private final StudentInfoRepository studentInfoRepository;
  private final MethodHelper methodHelper;
  private final LessonService lessonService;
  private final EducationTermService educationTermService;
  private final StudentInfoHelper studentInfoHelper;
  private final StudentInfoMapper studentInfoMapper;

  public ResponseMessage<StudentInfoResponse> saveStudentInfo(HttpServletRequest httpServletRequest,
      StudentInfoRequest studentInfoRequest) {
    String teacherUserName = (String) httpServletRequest.getAttribute("username");
    User teacher = methodHelper.loadByUsername(teacherUserName);
    //validate student id
    User student = methodHelper.isUserExist(studentInfoRequest.getStudentId());
    //validate user is really a student
    methodHelper.checkUserRole(student, RoleType.STUDENT);
    //validate and fetch lesson
    Lesson lesson = lessonService.isLessonExistById(studentInfoRequest.getLessonId());
    //validate and fetch education term
    EducationTerm educationTerm = educationTermService.isEducationTermExist(studentInfoRequest.getEducationTermId());
    //student should have only one student info for a lesson
    studentInfoHelper.validateLessonDuplication(studentInfoRequest.getStudentId(),
        lesson.getLessonName());
    Note note = studentInfoHelper.checkLetterGrade(studentInfoHelper.calculateAverageScore(
        studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam()));
    //mapping
    StudentInfo studentInfo = studentInfoMapper.mapStudentInfoRequestToStudentInfo(
        studentInfoRequest,
        note,
        studentInfoHelper.calculateAverageScore(studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam()));
    //set missing props.
    studentInfo.setStudent(student);
    studentInfo.setLesson(lesson);
    studentInfo.setEducationTerm(educationTerm);
    studentInfo.setTeacher(teacher);
    StudentInfo savedStudentInfo = studentInfoRepository.save(studentInfo);
    return ResponseMessage.<StudentInfoResponse>builder()
        .message(SuccessMessages.STUDENT_INFO_SAVE)
        .returnBody(studentInfoMapper.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
        .build();
  }

    public ResponseMessage<StudentInfoResponse> updateStudentInfo(@Valid StudentInfoUpdateRequest studentInfoUpdateRequest, Long id) {
      // 1. Find the existing StudentInfo record
      StudentInfo existingStudentInfo = studentInfoRepository.findById(id)
              .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.STUDENT_INFO_NOT_FOUND));

      // 2. Validate the student
      User student = methodHelper.isUserExist(studentInfoUpdateRequest.getStudentId());
      methodHelper.checkUserRole(student, RoleType.STUDENT);

      // 3. Validate the lesson and education term
      Lesson lesson = lessonService.isLessonExistById(studentInfoUpdateRequest.getLessonId());
      EducationTerm educationTerm = educationTermService.isEducationTermExist(studentInfoUpdateRequest.getEducationTermId());

      // 4. Calculate the new average score and determine the letter grade
      double averageScore = studentInfoHelper.calculateAverageScore(studentInfoUpdateRequest.getMidtermExam(), studentInfoUpdateRequest.getFinalExam());
      Note updatedNote = studentInfoHelper.checkLetterGrade(averageScore);

      // 5. Update using the mapper
      StudentInfo updatedStudentInfo = studentInfoMapper.mapStudentInfoRequestToStudentInfo(
              studentInfoUpdateRequest,
              updatedNote,
              averageScore
      );

      // 6. Set missing properties
      updatedStudentInfo.setId(existingStudentInfo.getId()); // Keep the existing ID
      updatedStudentInfo.setStudent(student);
      updatedStudentInfo.setLesson(lesson);
      updatedStudentInfo.setEducationTerm(educationTerm);
      updatedStudentInfo.setTeacher(existingStudentInfo.getTeacher()); // Do not change the teacher

      // 7. Save the updated StudentInfo record
      StudentInfo savedStudentInfo = studentInfoRepository.save(updatedStudentInfo);

      // 8. Create a response using the mapper and return it
      return ResponseMessage.<StudentInfoResponse>builder()
              .message(SuccessMessages.STUDENT_INFO_UPDATE)
              .returnBody(studentInfoMapper.mapStudentInfoToStudentInfoResponse(savedStudentInfo))
              .build();
  }
}
