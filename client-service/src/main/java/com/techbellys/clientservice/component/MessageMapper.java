package com.techbellys.clientservice.component;

import com.techbellys.clientservice.dto.MessageClientRequest;
import com.techbellys.clientservice.dto.MessageClientResponse;
import com.techbellys.clientservice.dto.PersonClient;


import com.techbellys.grpcinterface.utils.TimeUtils;
import com.techbellys.messaging.MessageRequest;
import com.techbellys.messaging.MessageResponse;
import com.techbellys.messaging.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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

}