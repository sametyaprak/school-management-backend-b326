package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.concretes.business.EducationTerm;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.Lesson;
import com.techproed.schoolmanagementbackendb326.entity.concretes.business.StudentInfo;
import com.techproed.schoolmanagementbackendb326.entity.concretes.user.User;
import com.techproed.schoolmanagementbackendb326.entity.enums.Note;
import com.techproed.schoolmanagementbackendb326.entity.enums.RoleType;
import com.techproed.schoolmanagementbackendb326.payload.mappers.StudentInfoMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.StudentInfoRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.payload.response.business.StudentInfoResponse;
import com.techproed.schoolmanagementbackendb326.repository.businnes.StudentInfoRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import com.techproed.schoolmanagementbackendb326.service.helper.StudentInfoHelper;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
  private final PageableHelper pageableHelper;

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
    EducationTerm educationTerm = educationTermService.isEducationTermExist(
        studentInfoRequest.getEducationTermId());
    //student should have only one student info for a lesson
    studentInfoHelper.validateLessonDuplication(studentInfoRequest.getStudentId(),
        lesson.getLessonName());
    Note note = studentInfoHelper.checkLetterGrade(studentInfoHelper.calculateAverageScore(
        studentInfoRequest.getMidtermExam(), studentInfoRequest.getFinalExam()));
    //mapping
    StudentInfo studentInfo = studentInfoMapper.mapStudentInfoRequestToStudentInfo(
        studentInfoRequest,
        note,
        studentInfoHelper.calculateAverageScore(studentInfoRequest.getMidtermExam(),
            studentInfoRequest.getFinalExam()));
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


  
  public Page<StudentInfoResponse> findByTeacherOrStudentByPage(HttpServletRequest servletRequest,
      int page, int size) {
    //preparing the pageable
    Pageable pageable = pageableHelper.getPageableByPageAndSize(page, size);
    //finding out who logged in
    String username = (String) servletRequest.getAttribute("username");
    User loggedInUser = methodHelper.loadByUsername(username);
    //checking if this is a Teacher or Student.
    //Since this endpoint is accessible by only Teacher or Student,
    //no need to check for other roles
    //used ternary for assigning
    Page<StudentInfo> studentInfoPage =
        (loggedInUser.getUserRole().getRoleType().getName().equals(RoleType.TEACHER.getName())) ?
            studentInfoRepository.findAllByTeacher_Id(loggedInUser.getId(), pageable)
            : studentInfoRepository.findAllByStudent_Id(loggedInUser.getId(), pageable);

    //SECOND OPTION
    // Teachers already has its own StudentInfo list within the object.
    // If loggedIn user is a teacher, we can have and turn that StudentInfo list into a Page directly.*/
    /*Page<StudentInfo> studentInfoPage =
        (loggedInUser.getUserRole().getRoleType().getName().equals(RoleType.TEACHER.getName())) ?
            new PageImpl<>(loggedInUser.getStudentInfos(), pageable,
                loggedInUser.getStudentInfos().size())
            : studentInfoRepository.findAllByStudent_Id(loggedInUser.getId(), pageable);*/

    return studentInfoPage.map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);
    
  }

  
  public Page<StudentInfoResponse> findStudentInfoByPage(int page, int size, String sort,
      String type) {
    Pageable pageable = pageableHelper.getPageable(page, size, sort, type);
    Page<StudentInfo> studentInfos = studentInfoRepository.findAll(pageable);
    return studentInfos.map(studentInfoMapper::mapStudentInfoToStudentInfoResponse);

  }
  
}
