package com.techbellys.clientservice.service;

import com.techbellys.clientservice.component.MessageMapper;
import com.techbellys.clientservice.dto.MessageClientRequest;
import com.techbellys.clientservice.dto.MessageClientResponse;
import com.techbellys.messaging.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
@Slf4j
public class MessagingService {
	private final MessagingServiceGrpc.MessagingServiceBlockingStub messagingServiceBlockingStub;
	private final MessageMapper messageMapper;
	
	public MessageClientResponse sendMessage(MessageClientRequest messageClientRequest) {
		MessageRequest serverRequest = messageMapper.toServerRequest(messageClientRequest);
		
		MessageResponse serverResponse = messagingServiceBlockingStub.sendMessage(serverRequest);
		
		return messageMapper.toClientResponse(serverResponse);
	}
}
