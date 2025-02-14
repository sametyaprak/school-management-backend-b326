package com.techproed.schoolmanagementbackendb326.service;

import com.techproed.schoolmanagementbackendb326.entity.ContactMessage;
import com.techproed.schoolmanagementbackendb326.exception.ResourceNotFoundException;
import com.techproed.schoolmanagementbackendb326.payload.mappers.ContactMapper;
import com.techproed.schoolmanagementbackendb326.payload.messages.ErrorMessages;
import com.techproed.schoolmanagementbackendb326.payload.messages.SuccessMessages;
import com.techproed.schoolmanagementbackendb326.payload.request.ContactRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.ContactResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.repository.ContactRepository;
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
public class ContactService {

	private final ContactRepository contactRepository;
	private final ContactMapper contactMapper;
	private final PageableHelper pageableHelper;
	private final MethodHelper methodHelper;

	public ResponseMessage<ContactResponse> saveMessage(
				@Valid ContactRequest contactRequest) {

		ContactMessage savedMessage = contactMapper.mapToEntity(contactRequest);

		contactRepository.save(savedMessage);
		return ResponseMessage.<ContactResponse>builder()
					       .message(SuccessMessages.MESSAGE_CREATE)
					       .returnBody(contactMapper.mapToResponse(savedMessage))
					       .httpStatus(HttpStatus.CREATED)
					       .build();
	}


	public ResponseMessage<List<ContactResponse>> getAllMessages() {
		List<ContactMessage> allMessages = contactRepository.findAll();
		if (allMessages.isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_MESSAGE);
		}
		return ResponseMessage.<List<ContactResponse>>builder()
					       .message(SuccessMessages.ALL_MESSAGES_FETCHED)
					       .returnBody(contactMapper.mapToResponseList(allMessages))
					       .httpStatus(HttpStatus.OK)
					       .build();
	}

	public Page<ContactResponse> getMessagesByPage(
				int page,
				int size,
				String sort,
				String type) {
		if (contactRepository.findAll(pageableHelper.getPageable(page, size, sort, type))
					    .isEmpty()) {
			throw new ResourceNotFoundException(ErrorMessages.NOT_FOUND_MESSAGE);
		}
		return contactRepository.findAll(pageableHelper.getPageable(page, size, sort, type))
					       .map(contactMapper::mapToResponse);
	}

	public ResponseMessage<List<ContactResponse>> getMessageByEmail(
				String email) {
		List<ContactMessage> messagesByEmail = contactRepository.findByEmail(email);
		if (messagesByEmail.isEmpty()) {
			throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_MESSAGE_BY_EMAIL, email));
		}
		return ResponseMessage.<List<ContactResponse>>builder()
					       .message(String.format(SuccessMessages.MESSAGES_FETCHED_BY_EMAIL, email))
					       .returnBody(contactMapper.mapToResponseList(messagesByEmail))
					       .httpStatus(HttpStatus.OK)
					       .build();
	}

	public ResponseMessage<List<ContactResponse>> getMessagesByCreationDateBetween(
				String startDate,
				String endDate) {
		LocalDateTime startDateTime = LocalDate.parse(startDate)
					                              .atStartOfDay();
		LocalDateTime endDateTime = LocalDate.parse(endDate)
					                            .atStartOfDay();

		List<ContactMessage> messagesByCreationDateBetween = contactRepository.findByCreatedAtBetween(startDateTime, endDateTime);
		if (messagesByCreationDateBetween.isEmpty()) {
			throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_MESSAGE_BETWEEN_DATES, startDate, endDate));
		}
		return ResponseMessage.<List<ContactResponse>>builder()
					       .message(String.format(SuccessMessages.MESSAGE_FOUND_BETWEEN_DATES, startDate, endDate))
					       .returnBody(contactMapper.mapToResponseList(messagesByCreationDateBetween))
					       .httpStatus(HttpStatus.OK)
					       .build();
	}

	public ResponseMessage<List<ContactResponse>> getMessagesByCreationTimeBetween(
				String startTime,
				String endTime) {

		List<ContactMessage> messagesByCreationTimeBetween = contactRepository.findByCreatedAtTimeBetween(startTime, endTime);

		if (messagesByCreationTimeBetween.isEmpty()) {
			throw new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_MESSAGE_BETWEEN_TIMES, startTime, endTime));
		}
		return ResponseMessage.<List<ContactResponse>>builder()
					       .message(String.format(SuccessMessages.MESSAGE_FOUND_BETWEEN_HOURS, startTime, endTime))
					       .returnBody(contactMapper.mapToResponseList(messagesByCreationTimeBetween))
					       .httpStatus(HttpStatus.OK)
					       .build();
	}

	public String deleteMessageById(
				Long messageId) {
		methodHelper.checkMessageExistById(messageId);
		contactRepository.deleteById(messageId);
		return String.format(SuccessMessages.MESSAGE_DELETE, messageId);
	}

	public ResponseMessage<ContactResponse> updateMessageById(
				@Valid ContactRequest contactRequest,
				Long messageId) {
		ContactMessage messageFromDb = methodHelper.checkMessageExistById(messageId);
		ContactMessage updatedMessage = contactMapper.mapToEntity(contactRequest);
		if (!messageFromDb.getName()
					     .equals(updatedMessage.getName())) {
			messageFromDb.setName(updatedMessage.getName());
		}
		if (!messageFromDb.getEmail()
					     .equals(updatedMessage.getEmail())) {
			messageFromDb.setEmail(updatedMessage.getEmail());
		}

		if (!messageFromDb.getSubject()
					     .equals(updatedMessage.getSubject())) {
			messageFromDb.setSubject(updatedMessage.getSubject());
		}

		if (!messageFromDb.getMessage()
					     .equals(updatedMessage.getMessage())) {
			messageFromDb.setMessage(updatedMessage.getMessage());
		}
		contactRepository.save(messageFromDb);
		return ResponseMessage.<ContactResponse>builder()
					       .message(SuccessMessages.MESSAGE_UPDATE)
					       .returnBody(contactMapper.mapToResponse(messageFromDb))
					       .httpStatus(HttpStatus.OK)
					       .build();

	}

	public ResponseMessage<List<ContactResponse>> searchMessageBySubject(
				String subject) {
		List<ContactMessage> messagesBySubject = methodHelper.checkMessageExistBySubject(subject);
		return ResponseMessage.<List<ContactResponse>>builder()
					       .message(String.format(SuccessMessages.MESSAGE_FOUND_BY_SUBJECT, subject))
					       .returnBody(contactMapper.mapToResponseList(messagesBySubject))
					       .httpStatus(HttpStatus.OK)
					       .build();
	}


}
