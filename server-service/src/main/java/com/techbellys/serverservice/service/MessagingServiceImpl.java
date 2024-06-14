package com.techbellys.serverservice.service;

import com.techbellys.grpcinterface.utils.TimeUtils;
import com.techbellys.messaging.*;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.Instant;

@GrpcService
@Slf4j
@AllArgsConstructor
public class MessagingServiceImpl extends MessagingServiceGrpc.MessagingServiceImplBase {

	@Override
	public void sendMessage(MessageRequest request, StreamObserver<MessageResponse> responseObserver) {
		Instant now = Instant.now();
		log.info("Received message from {}: {}", request.getSender().getName(), request.getContent());

		MessageResponse response = MessageResponse.newBuilder()
				.setRecipient(request.getSender().getName())
				.setContent(request.getContent())
				.setReadTime(TimeUtils.convertToTimestamp(now))
				.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}
