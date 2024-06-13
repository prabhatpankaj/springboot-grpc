package com.techbellys.config;

import com.techbellys.category.CategoryServiceGrpc;
import com.techbellys.messaging.MessagingServiceGrpc;
import com.techbellys.order.OrderServiceGrpc;
import com.techbellys.product.ProductServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrcpClientConfig {
//	@Value("${grpc.client.server-service.address}")
//	private String serverServiceAddress;
//
//	@Value("${grpc.client.server-service.port}")
//	private int serverServicePort;
//
//	@Value("${grpc.client.server-service.negotiationType}")
//	private String serverServiceNegotiationType;
//
//	@Bean
//	public ManagedChannel serverServiceChannel() {
//		ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(serverServiceAddress, serverServicePort);
//		if ("plaintext".equalsIgnoreCase(serverServiceNegotiationType)) {
//			channelBuilder.usePlaintext();
//		}
//		return channelBuilder.build();
//	}
//
//	@Bean
//	public CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub(ManagedChannel serverServiceChannel) {
//		return CategoryServiceGrpc.newBlockingStub(serverServiceChannel);
//	}
//
//	@Bean
//	public CategoryServiceGrpc.CategoryServiceStub categoryServiceStub(ManagedChannel serverServiceChannel) {
//		return CategoryServiceGrpc.newStub(serverServiceChannel);
//	}
//
//	@Bean
//	public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub(ManagedChannel serverServiceChannel) {
//		return ProductServiceGrpc.newBlockingStub(serverServiceChannel);
//	}
//
//	@Bean
//	public ProductServiceGrpc.ProductServiceStub productServiceStub(ManagedChannel serverServiceChannel) {
//		return ProductServiceGrpc.newStub(serverServiceChannel);
//	}
//
//	@Bean
//	public OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub(ManagedChannel serverServiceChannel) {
//		return OrderServiceGrpc.newBlockingStub(serverServiceChannel);
//	}
//
//	@Bean
//	public OrderServiceGrpc.OrderServiceStub orderServiceStub(ManagedChannel serverServiceChannel) {
//		return OrderServiceGrpc.newStub(serverServiceChannel);
//	}

	@Value("${grpc.client.grpc-category-service.address}")
	private String categoryServiceAddress;

	@Value("${grpc.client.grpc-category-service.port}")
	private int categoryServicePort;

	@Value("${grpc.client.grpc-category-service.negotiationType}")
	private String categoryServiceNegotiationType;

	@Value("${grpc.client.grpc-product-service.address}")
	private String productServiceAddress;

	@Value("${grpc.client.grpc-product-service.port}")
	private int productServicePort;

	@Value("${grpc.client.grpc-product-service.negotiationType}")
	private String productServiceNegotiationType;

	@Value("${grpc.client.grpc-order-service.address}")
	private String orderServiceAddress;

	@Value("${grpc.client.grpc-order-service.port}")
	private int orderServicePort;

	@Value("${grpc.client.grpc-order-service.negotiationType}")
	private String orderServiceNegotiationType;

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

	@Bean
	public ManagedChannel categoryServiceChannel() {
		ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(categoryServiceAddress, categoryServicePort);
		if ("plaintext".equalsIgnoreCase(categoryServiceNegotiationType)) {
			channelBuilder.usePlaintext();
		}
		return channelBuilder.build();
	}

	@Bean
	public CategoryServiceGrpc.CategoryServiceBlockingStub categoryServiceBlockingStub(ManagedChannel categoryServiceChannel) {
		return CategoryServiceGrpc.newBlockingStub(categoryServiceChannel);
	}

	@Bean
	public ManagedChannel productServiceChannel() {
		ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(productServiceAddress, productServicePort);
		if ("plaintext".equalsIgnoreCase(productServiceNegotiationType)) {
			channelBuilder.usePlaintext();
		}
		return channelBuilder.build();
	}

	@Bean
	public ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub(ManagedChannel productServiceChannel) {
		return ProductServiceGrpc.newBlockingStub(productServiceChannel);
	}

	@Bean
	public ManagedChannel orderServiceChannel() {
		ManagedChannelBuilder<?> channelBuilder = ManagedChannelBuilder.forAddress(orderServiceAddress, orderServicePort);
		if ("plaintext".equalsIgnoreCase(orderServiceNegotiationType)) {
			channelBuilder.usePlaintext();
		}
		return channelBuilder.build();
	}

	@Bean
	public OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub(ManagedChannel orderServiceChannel) {
		return OrderServiceGrpc.newBlockingStub(orderServiceChannel);
	}
}


