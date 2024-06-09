package com.techbellys.clientservice.controller;

import com.techbellys.clientservice.dto.MessageClientRequest;
import com.techbellys.clientservice.dto.MessageClientResponse;
import com.techbellys.clientservice.service.MessagingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/messaging")
@AllArgsConstructor
public class MessagingController {
	private final MessagingService messagingService;
	
	@PostMapping(value = "/send")
	public ResponseEntity<MessageClientResponse> sendMessage(@RequestBody MessageClientRequest messageClientRequest) {
		return new ResponseEntity<>(messagingService.sendMessage(messageClientRequest), HttpStatus.CREATED);
	}
}
