package com.techbellys.clientservice.config;

import com.techbellys.messaging.MessagingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrcpClientConfig {

	@Value("${grpc.client.messaging-server-service.address}")
	private String address;

	@Value("${grpc.client.messaging-server-service.port}")
	private int port;

	@Value("${grpc.client.messaging-server-service.negotiationType}")
	private String negotiationType;

	@Bean
	ManagedChannel channel() {
		ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(address, port);
		if ("plaintext".equalsIgnoreCase(negotiationType)) {
			channelBuilder.usePlaintext();
		}
		return channelBuilder.build();
	}

	@Bean
	public MessagingServiceGrpc.MessagingServiceBlockingStub messagingServiceBlockingStub(ManagedChannel channel) {
		return MessagingServiceGrpc.newBlockingStub(channel);
	}

	@Bean
	public MessagingServiceGrpc.MessagingServiceStub messagingServiceStub(ManagedChannel channel) {
		return MessagingServiceGrpc.newStub(channel);
	}
}
