package com.techproed.schoolmanagementbackendb326.service.businnes;

import com.techproed.schoolmanagementbackendb326.entity.ContactMessage;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.ContactMessageMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.business.ContactMessageRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ContactMessageResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.businnes.ContactMessageRepository;
import com.techproed.schoolmanagementbackendb326.service.helper.MethodHelper;
import com.techproed.schoolmanagementbackendb326.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactMessageService {

  private final ContactMessageRepository contactMessageRepository;
  private final ContactMessageMapper contactMessageMapper;
  private final PageableHelper pageableHelper;
  private final MethodHelper methodHelper;

  public ResponseMessage<ContactMessageResponse> saveContactMessage(
      @Valid ContactMessageRequest contactMessageRequest) {

    ContactMessage savedMessage = contactMessageMapper.mapToEntity(contactMessageRequest);

    contactMessageRepository.save(savedMessage);
    return ResponseMessage.<ContactMessageResponse>builder()
        .message(SuccessMessages.MESSAGE_CREATE)
        .returnBody(contactMessageMapper.mapToResponse(savedMessage))
        .httpStatus(HttpStatus.CREATED)
        .build();
  }


  public ResponseMessage<List<ContactMessageResponse>> getAllContactMessages() {
    List<ContactMessage> allMessages = contactMessageRepository.findAll();
    if (allMessages.isEmpty()) {
      throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_MESSAGE);
    }
    return ResponseMessage.<List<ContactMessageResponse>>builder()
        .message(SuccessMessages.ALL_MESSAGES_FETCHED)
        .returnBody(contactMessageMapper.mapToResponseList(allMessages))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public Page<ContactMessageResponse> getContactMessagesByPage(
      int page,
      int size,
      String sort,
      String type) {
    if (contactMessageRepository.findAll(pageableHelper.getPageable(page, size, sort, type))
        .isEmpty()) {
      throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_MESSAGE);
    }
    return contactMessageRepository.findAll(pageableHelper.getPageable(page, size, sort, type))
        .map(contactMessageMapper::mapToResponse);
  }

  public ResponseMessage<List<ContactMessageResponse>> getContactMessageByEmail(
      String email) {
    List<ContactMessage> messagesByEmail = contactMessageRepository.findByEmail(email);
    if (messagesByEmail.isEmpty()) {
      throw new ResourceNotFoundException(
          String.format(ErrorMessages.NOT_FOUND_MESSAGE_BY_EMAIL, email));
    }
    return ResponseMessage.<List<ContactMessageResponse>>builder()
        .message(String.format(SuccessMessages.MESSAGES_FETCHED_BY_EMAIL, email))
        .returnBody(contactMessageMapper.mapToResponseList(messagesByEmail))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public ResponseMessage<List<ContactMessageResponse>> getContactMessagesByCreationDateBetween(
      String startDate,
      String endDate) {
    LocalDateTime startDateTime = LocalDate.parse(startDate)
        .atStartOfDay();
    LocalDateTime endDateTime = LocalDate.parse(endDate)
        .atStartOfDay();

    List<ContactMessage> messagesByCreationDateBetween = contactMessageRepository.findByCreatedAtBetween(
        startDateTime, endDateTime);
    if (messagesByCreationDateBetween.isEmpty()) {
      throw new ResourceNotFoundException(
          String.format(ErrorMessages.NOT_FOUND_MESSAGE_BETWEEN_DATES, startDate, endDate));
    }
    return ResponseMessage.<List<ContactMessageResponse>>builder()
        .message(String.format(SuccessMessages.MESSAGE_FOUND_BETWEEN_DATES, startDate, endDate))
        .returnBody(contactMessageMapper.mapToResponseList(messagesByCreationDateBetween))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public ResponseMessage<List<ContactMessageResponse>> getContactMessagesByCreationTimeBetween(
      String startTime,
      String endTime) {

    List<ContactMessage> messagesByCreationTimeBetween = contactMessageRepository.findByCreatedAtTimeBetween(
        startTime, endTime);

    if (messagesByCreationTimeBetween.isEmpty()) {
      throw new ResourceNotFoundException(
          String.format(ErrorMessages.NOT_FOUND_MESSAGE_BETWEEN_TIMES, startTime, endTime));
    }
    return ResponseMessage.<List<ContactMessageResponse>>builder()
        .message(String.format(SuccessMessages.MESSAGE_FOUND_BETWEEN_HOURS, startTime, endTime))
        .returnBody(contactMessageMapper.mapToResponseList(messagesByCreationTimeBetween))
        .httpStatus(HttpStatus.OK)
        .build();
  }

  public String deleteContactMessageById(
      Long messageId) {
    methodHelper.checkContactMessageExistById(messageId);
    contactMessageRepository.deleteById(messageId);
    return String.format(SuccessMessages.MESSAGE_DELETE, messageId);
  }

  public ResponseMessage<ContactMessageResponse> updateContactMessageById(
      @Valid ContactMessageRequest contactMessageRequest,
      Long contactMessageId) {
    ContactMessage contactMessageFromDb = methodHelper.checkContactMessageExistById(
        contactMessageId);
    ContactMessage updatedContactMessage = contactMessageMapper.mapToEntity(contactMessageRequest);
    if (!contactMessageFromDb.getName()
        .equals(updatedContactMessage.getName())) {
      contactMessageFromDb.setName(updatedContactMessage.getName());
    }
    if (!contactMessageFromDb.getEmail()
        .equals(updatedContactMessage.getEmail())) {
      contactMessageFromDb.setEmail(updatedContactMessage.getEmail());
    }

    if (!contactMessageFromDb.getSubject()
        .equals(updatedContactMessage.getSubject())) {
      contactMessageFromDb.setSubject(updatedContactMessage.getSubject());
    }

    if (!contactMessageFromDb.getMessage()
        .equals(updatedContactMessage.getMessage())) {
      contactMessageFromDb.setMessage(updatedContactMessage.getMessage());
    }
    contactMessageRepository.save(contactMessageFromDb);
    return ResponseMessage.<ContactMessageResponse>builder()
        .message(SuccessMessages.MESSAGE_UPDATE)
        .returnBody(contactMessageMapper.mapToResponse(contactMessageFromDb))
        .httpStatus(HttpStatus.OK)
        .build();

  }

  public ResponseMessage<List<ContactMessageResponse>> searchContactMessageBySubject(
      String subject) {
    List<ContactMessage> contactMessagesBySubject = methodHelper.checkContactMessageExistBySubject(
        subject);
    return ResponseMessage.<List<ContactMessageResponse>>builder()
        .message(String.format(SuccessMessages.MESSAGE_FOUND_BY_SUBJECT, subject))
        .returnBody(contactMessageMapper.mapToResponseList(contactMessagesBySubject))
        .httpStatus(HttpStatus.OK)
        .build();
  }


}
