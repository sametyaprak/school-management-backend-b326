package com.techproed.schoolmanagementbackendb326.payload.messages;

public class ErrorMessages {

  private ErrorMessages() {
  }


  //user
  public static final String NOT_HAVE_EXPECTED_ROLE_USER = "Error: User does not have expected role";
  public static final String NOT_FOUND_USER_MESSAGE = "Error: User not found with id %s";
  public static final String NOT_FOUND_USER_MESSAGE_USERNAME = "Error: User not found with username %s";
  public static final String NOT_PERMITTED_METHOD_MESSAGE = "You do not have any permission to do this operation";
  public static final String PASSWORD_SHOULD_NOT_MATCHED = "Your passwords are not matched" ;
  public static final String PASSWORD_SHOULD_NOT_MATCHED_HISTORY = "The new password cannot be the same as any of the last 3 passwords. Please choose a different password.";

  //student
  public static final String STUDENT_INFO_NOT_FOUND = "Error: Student Info with id %d not found" ;

  //advisor teacher
  public static final String NOT_FOUND_ADVISOR_MESSAGE = "Error: Advisor Teacher with id %s not found" ;
  public static final String NOT_ADVISOR_TEACHER_MESSAGE = "Error: Teacher with id %s is not an advisor teacher" ;

  //unique properties
  public static final String ALREADY_REGISTER_MESSAGE_USERNAME = "Error: User with username %s is already registered";
  public static final String ALREADY_REGISTER_MESSAGE_SSN = "Error: User with ssn %s is already registered";
  public static final String ALREADY_REGISTER_MESSAGE_PHONE_NUMBER = "Error: User with phone number %s is already registered";
  public static final String ALREADY_REGISTER_MESSAGE_EMAIL = "Error: User with email %s is already registered";

  //user roles
  public static final String ROLE_NOT_FOUND = "There is no role like that, check the database";
  public static final String NOT_FOUND_USER_USER_ROLE_MESSAGE = "Error: User not found with user-role %s";

  //education term
  public static final String EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE = "Error: The start date cannot be earlier than the last registration date " ;
  public static final String EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE = "Error: The end date cannot be earlier than the start date " ;
  public static final String EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE = "Error: Education Term with Term And Year already exist " ;
  public static final String EDUCATION_TERM_NOT_FOUND_MESSAGE = "Error: Education Term with id %s not found" ;
  public static final String EDUCATION_TERM_CONFLICT_MESSAGE = "Error: There is a conflict regarding the dates of the education terms.";

  //lesson
  public static final String ALREADY_REGISTER_LESSON_MESSAGE = "Error: Lesson with lesson name %s already registered" ;
  public static final String ALREADY_CREATED_LESSON_MESSAGE = "Error: %s Lesson already exist";
  public static final String NOT_FOUND_LESSON_MESSAGE = "Error: Lesson with id %s not found";
  public static final String NOT_FOUND_LESSON_IN_LIST = "Error: Lesson not found in the list" ;
  public static final String TIME_NOT_VALID_MESSAGE = "Error: incorrect time" ;

  //lesson program
  public static final String NOT_FOUND_LESSON_PROGRAM_MESSAGE = "Error: Lesson program with id, %s not found";
  public static final String NOT_FOUND_LESSON_PROGRAM_MESSAGE_WITHOUT_ID_INFO = "Error: Lesson program with this field not found";
  public static final String LESSON_PROGRAM_ALREADY_EXIST = "Error: Course schedule can not be selected for the same hour and date" ;

  //meet
  public static final String MEET_NOT_FOUND_MESSAGE = "Error: Meet with id %d not found" ;
  public static final String MEET_HOURS_CONFLICT = "meet hours has conflict with existing meets";

  //contactapp
  public static final String NOT_FOUND_MESSAGE = "Message not Found with ID : %s";
  public static final String NOT_FOUND_MESSAGE_BY_EMAIL = "Message not Found by Email : %s";
  public static final String NOT_FOUND_MESSAGE_BETWEEN_DATES = "Message not Found between Dates : %s and %s";
  public static final String NOT_FOUND_MESSAGE_BETWEEN_TIMES = "Message not Found between Times : %s and %s";
  public static final String NOT_FOUND_MESSAGE_BY_SUBJECT = "Message not Found by Subject : %s";

  //studentInfo
  public static final String ALREADY_CREATED_STUDENT_INFO_FOR_LESSON = "Error: %s Student info already created";

}
