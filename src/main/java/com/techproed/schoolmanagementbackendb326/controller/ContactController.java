package com.techproed.schoolmanagementbackendb326.controller;

import com.techproed.schoolmanagementbackendb326.payload.request.ContactRequest;
import com.techproed.schoolmanagementbackendb326.payload.response.ContactResponse;
import com.techproed.schoolmanagementbackendb326.payload.response.business.ResponseMessage;
import com.techproed.schoolmanagementbackendb326.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/contact")
public class ContactController {

	private final ContactService contactService;

	@PostMapping("/save")
	public ResponseEntity<ResponseMessage<ContactResponse>> saveMessage(
				@RequestBody @Valid ContactRequest contactRequest) {
		return ResponseEntity.ok(contactService.saveMessage(contactRequest));
	}

	@GetMapping("/getAllMessages")
	public ResponseEntity<ResponseMessage<List<ContactResponse>>> getAllMessages() {
		return ResponseEntity.ok(contactService.getAllMessages());
	}

	@GetMapping("/getMessagesByPage")
	public ResponseEntity<Page<ContactResponse>> getMessagesByPage(
				@RequestParam(value = "page", defaultValue = "0") int page,
				@RequestParam(value = "size", defaultValue = "10") int size,
				@RequestParam(value = "sort", defaultValue = "createdAt") String sort,
				@RequestParam(value = "type", defaultValue = "desc") String type) {
		return ResponseEntity.ok(contactService.getMessagesByPage(page, size, sort, type));
	}

	@GetMapping("/searchMessageBySubject")
	public ResponseEntity<ResponseMessage<List<ContactResponse>>> searchMessageBySubject(
				@RequestParam(value = "subject") String subject) {
		return ResponseEntity.ok(contactService.searchMessageBySubject(subject));
	}

	@GetMapping("/getMessageByEmail")
	public ResponseEntity<ResponseMessage<List<ContactResponse>>> getMessageByEmail(
				@RequestParam(value = "email") String email) {
		return ResponseEntity.ok(contactService.getMessageByEmail(email));
	}


	@GetMapping("/getMessagesByCreationDateBetween")
	public ResponseEntity<ResponseMessage<List<ContactResponse>>> getMessagesByCreationDateBetween(
				@RequestParam(value = "startDate") String startDate,
				@RequestParam(value = "endDate") String endDate) {
		return ResponseEntity.ok(contactService.getMessagesByCreationDateBetween(startDate, endDate));
	}

	@GetMapping("getMessagesByCreationHourBetween")
	public ResponseEntity<ResponseMessage<List<ContactResponse>>> getMessagesByCreationHourBetween(
				@RequestParam(value = "startHour") String startHour,
				@RequestParam(value = "endHour") String endHour) {
		return ResponseEntity.ok(contactService.getMessagesByCreationTimeBetween(startHour, endHour));
	}

	@DeleteMapping("/deleteMessageById/{messageId}")
	public ResponseEntity<String> deleteMessageById(
				@PathVariable Long messageId) {
		return ResponseEntity.ok(contactService.deleteMessageById(messageId));
	}

	@DeleteMapping("/deleteMessageById")
	public ResponseEntity<String> deleteMessageByIdPath(
				@RequestParam Long messageId) {
		return ResponseEntity.ok(contactService.deleteMessageById(messageId));
	}

	@PutMapping("/updateMessageById/{messageId}")
	public ResponseEntity<ResponseMessage<ContactResponse>> updateMessageById(
				@RequestBody @Valid ContactRequest contactRequest,
				@PathVariable Long messageId) {
		return ResponseEntity.ok(contactService.updateMessageById(contactRequest, messageId));
	}
}

