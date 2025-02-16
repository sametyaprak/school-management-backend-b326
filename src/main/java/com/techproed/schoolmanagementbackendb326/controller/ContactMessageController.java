package com.techproed.schoolmanagementbackendb326.controller;

import com.techproed.schoolmanagementbackendb326.payload.request.ContactMessageRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.ContactMessageResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact")
public class ContactMessageController {

	private final ContactMessageService contactMessageService;

	@PostMapping("/save")
	public ResponseEntity<ResponseMessage<ContactMessageResponse>> saveContactMessage(
				@RequestBody @Valid ContactMessageRequest contactMessageRequest) {
		return ResponseEntity.ok(contactMessageService.saveContactMessage(contactMessageRequest));
	}

	@GetMapping("/getAllMessages")
	public ResponseEntity<ResponseMessage<List<ContactMessageResponse>>> getAllContactMessages() {
		return ResponseEntity.ok(contactMessageService.getAllContactMessages());
	}

	@GetMapping("/getMessagesByPage")
	public ResponseEntity<Page<ContactMessageResponse>> getContactMessagesByPage(
				@RequestParam(value = "page", defaultValue = "0") int page,
				@RequestParam(value = "size", defaultValue = "10") int size,
				@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
				@RequestParam(value = "type", defaultValue = "desc") String type) {
		return ResponseEntity.ok(contactMessageService.getContactMessagesByPage(page, size, sort, type));
	}

	@GetMapping("/searchMessageBySubject")
	public ResponseEntity<ResponseMessage<List<ContactMessageResponse>>> searchContactMessageBySubject(
				@RequestParam(value = "subject") String subject) {
		return ResponseEntity.ok(contactMessageService.searchContactMessageBySubject(subject));
	}

	@GetMapping("/getMessageByEmail")
	public ResponseEntity<ResponseMessage<List<ContactMessageResponse>>> getContactMessageByEmail(
				@RequestParam(value = "email") String email) {
		return ResponseEntity.ok(contactMessageService.getContactMessageByEmail(email));
	}


	@GetMapping("/getMessagesByCreationDateBetween")
	public ResponseEntity<ResponseMessage<List<ContactMessageResponse>>> getContactMessagesByCreationDateBetween(
				@RequestParam(value = "startDate") String startDate,
				@RequestParam(value = "endDate") String endDate) {
		return ResponseEntity.ok(contactMessageService.getContactMessagesByCreationDateBetween(startDate, endDate));
	}

	@GetMapping("getMessagesByCreationHourBetween")
	public ResponseEntity<ResponseMessage<List<ContactMessageResponse>>> getContactMessagesByCreationHourBetween(
				@RequestParam(value = "startHour") String startHour,
				@RequestParam(value = "endHour") String endHour) {
		return ResponseEntity.ok(contactMessageService.getContactMessagesByCreationTimeBetween(startHour, endHour));
	}

	@DeleteMapping("/deleteMessageById/{messageId}")
	public ResponseEntity<String> deleteContactMessageById(
				@PathVariable Long messageId) {
		return ResponseEntity.ok(contactMessageService.deleteContactMessageById(messageId));
	}

	@DeleteMapping("/deleteMessageById")
	public ResponseEntity<String> deleteContactMessageByIdPath(
				@RequestParam Long messageId) {
		return ResponseEntity.ok(contactMessageService.deleteContactMessageById(messageId));
	}

	@PutMapping("/updateMessageById/{messageId}")
	public ResponseEntity<ResponseMessage<ContactMessageResponse>> updateContactMessageById(
				@RequestBody @Valid ContactMessageRequest contactMessageRequest,
				@PathVariable Long messageId) {
		return ResponseEntity.ok(contactMessageService.updateContactMessageById(contactMessageRequest, messageId));
	}
}

