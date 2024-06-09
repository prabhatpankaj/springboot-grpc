package com.techbellys.serverservice.service;

import com.techbellys.grpcinterface.utils.TimeUtils;
import com.techbellys.messaging.MessageRequest;
import com.techbellys.messaging.MessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Slf4j
public class MessagingService {
	
	public MessageResponse sendMessage(MessageRequest request) {
		Instant now = Instant.now();
		log.info("Received message from {}: {}", request.getSender().getName(), request.getContent());
		
		return MessageResponse.newBuilder()
				.setContent("Hi " + request.getSender().getName() + ", I'm server!")
				.setRecipient(request.getSender().getName())
				.setReadTime(TimeUtils.convertToTimestamp(now))
				.build();
	}
}
