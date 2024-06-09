package com.techbellys.clientservice.component;

import com.techbellys.clientservice.dto.MessageClientRequest;
import com.techbellys.clientservice.dto.MessageClientResponse;
import com.techbellys.clientservice.dto.MessagesBySenderResponse;
import com.techbellys.clientservice.dto.PersonClient;


import com.techbellys.grpcinterface.utils.TimeUtils;
import com.techbellys.messaging.MessageRequest;
import com.techbellys.messaging.MessageResponse;
import com.techbellys.messaging.Person;
import com.techbellys.messaging.SenderMessagesPair;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {
	
	public MessageRequest toServerRequest(MessageClientRequest messageClientRequest) {
		return MessageRequest.newBuilder()
				.setContent(messageClientRequest.getContent())
				.setSender(toPersonServer(messageClientRequest.getSender()))
				.setSendTime(TimeUtils.convertToTimestamp(LocalDateTime.now()))
				.build();
	}
	
	public MessageClientResponse toClientResponse(MessageResponse messageResponse) {
		return MessageClientResponse.builder()
				.content(messageResponse.getContent())
				.recipient(messageResponse.getRecipient())
				.readTime(TimeUtils.convertToLocalDateTime(messageResponse.getReadTime()))
				.build();
	}
	
	private Person toPersonServer(PersonClient sender) {
		Person.Builder builder = Person.newBuilder()
				.setName(sender.getName());
		
		if (sender.getAge() != null) builder.setAge(sender.getAge());
		if (sender.getGender() != null) builder.setGender(sender.getGender());
		
		return builder.build();
	}
	
	private PersonClient toPersonClient(Person sender) {
		PersonClient.PersonClientBuilder builder = PersonClient.builder()
				.name(sender.getName());
		
		if (sender.hasAge()) builder.age(sender.getAge());
		if (sender.hasGender()) builder.gender(sender.getGender());
		
		return builder.build();
	}
	
	public List<MessagesBySenderResponse.SenderMessagesPairResponse> toClientResponse(
			List<SenderMessagesPair> senderMessagesList) {
		return senderMessagesList.stream()
				.map(serverResponse -> MessagesBySenderResponse.SenderMessagesPairResponse.builder()
						.sender(toPersonClient(serverResponse.getSender()))
						.messages(serverResponse.getMessagesList())
						.build())
				.collect(Collectors.toList());
	}
	
	
}
