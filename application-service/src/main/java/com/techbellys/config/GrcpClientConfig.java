package com.techbellys.config;

import com.techbellys.category.CategoryServiceGrpc;
import com.techbellys.order.OrderServiceGrpc;
import com.techbellys.product.ProductServiceGrpc;
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
	public CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub(ManagedChannel channel) {
		return CategoryServiceGrpc.newBlockingStub(channel);
	}

	@Bean
	public CategoryServiceGrpc.CategoryServiceStub categoryServiceStub(ManagedChannel channel) {
		return CategoryServiceGrpc.newStub(channel);
	}

	@Bean
	public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub(ManagedChannel channel) {
		return ProductServiceGrpc.newBlockingStub(channel);
	}

	@Bean
	public ProductServiceGrpc.ProductServiceStub productServiceStub(ManagedChannel channel) {
		return ProductServiceGrpc.newStub(channel);
	}

	@Bean
	public OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub(ManagedChannel channel) {
		return OrderServiceGrpc.newBlockingStub(channel);
	}

	@Bean
	public OrderServiceGrpc.OrderServiceStub orderServiceStub(ManagedChannel channel) {
		return OrderServiceGrpc.newStub(channel);
	}

}
