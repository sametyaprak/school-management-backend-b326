package com.techproed.schoolmanagementbackendb326.payload.messages;

public class SuccessMessages {

  private SuccessMessages() {
  }


  //contact message
  public static final String ALREADY_SEND_A_MESSAGE_TODAY = "Error: You have already send a message with this e-mail";
  public static final String WRONG_DATE_FORMAT = "Wrong Date Format";
  public static final String WRONG_TIME_FORMAT = "Wrong Time Format";
  public static final String NOT_FOUND_MESSAGE = "Message Not Found";
  public static final String CONTACT_MESSAGE_DELETED_SUCCESSFULLY = "Contact message deleted Successfully";

  //user
  public static final String USER_FOUND = "User is Found Successfully";
  public static final String USER_CREATE = "User is Saved";
  public static final String USER_DELETE = "User is deleted successfully";
  public static final String USER_UPDATE = "your information has been updated successfully";
  public static final String USER_UPDATE_MESSAGE = "User is Updated Successfully";
  public static final String PASSWORD_CHANGED_RESPONSE_MESSAGE = "Password Successfully Changed" ;

  //Student
  public static final String STUDENT_SAVE = "Student is Saved";
  public static final String STUDENT_UPDATE = "Student is Updated Successfully";

  //Teacher
  public static final String TEACHER_SAVE = "Teacher is Saved";
  public static final String TEACHER_UPDATE = "Teacher is Updated Successfully";

  //Advisor teacher
  public static final String ADVISOR_TEACHER_SAVE = "Advisor Teacher is Saved";
  public static final String ADVISOR_TEACHER_DELETE = "Advisor Teacher is Deleted";

  //Education term
  public static final String EDUCATION_TERM_SAVE = "Education Term is Saved";
  public static final String EDUCATION_TERM_UPDATE = "Education Term is Updated Successfully";
  public static final String EDUCATION_TERM_DELETE = "Education Term is Deleted Successfully";

  //Lesson
  public static final String LESSON_SAVE = "Lesson is Saved";
  public static final String LESSON_DELETE = "Lesson is Deleted Successfully";
  public static final String LESSON_FOUND = "Lesson is Found Successfully";

  //Lesson program
  public static final String LESSON_PROGRAM_SAVE = "Lesson Program is Saved";
  public static final String LESSON_PROGRAM_DELETE = "Lesson Program is Deleted Successfully";
  public static final String LESSON_PROGRAM_ADD_TO_TEACHER = "Lesson Program added to teacher";
  public static final String LESSON_PROGRAM_ADD_TO_STUDENT = "Lesson Program added to student";

  //Meet
  public static final String MEET_SAVE = "Meet is Saved";
  public static final String MEET_UPDATE = "Meet is Updated Successfully";
  public static final String MEET_DELETE = "Meet is Deleted Successfully";
  public static final String MEET_FOUND = "Meet is Found Successfully";

  //Student Info
  public static final String STUDENT_INFO_SAVE = "Student Info is Saved";
  public static final String STUDENT_INFO_UPDATE = "Student Info is Updated Successfully";
  public static final String STUDENT_INFO_DELETE = "Student Info is Deleted Successfully";

  //contactapp
  public static final String MESSAGE_FOUND_BETWEEN_HOURS = "Message is Found Successfully between Hours : %s and %s";
  public static final String MESSAGE_FOUND_BY_SUBJECT = "Messages are Found Successfully by Subject : %s";
  public static final String MESSAGE_FOUND_BETWEEN_DATES = "Message is Found Successfully between Dates : %s and %s";
  public static final String MESSAGE_CREATE = "Message is Saved";
  public static final String MESSAGE_DELETE = "Message is deleted successfully with ID : %s";
  public static final String MESSAGE_UPDATE = "Message has been updated successfully";
  public static final String ALL_MESSAGES_FETCHED = "All messages fetched successfully";
  public static final String MESSAGES_FETCHED_BY_EMAIL = "Messages fetched successfully by email : %s";

}
