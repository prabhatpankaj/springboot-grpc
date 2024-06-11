package com.techbellys.serverservice.config;

import net.devh.boot.grpc.server.security.authentication.BasicGrpcAuthenticationReader;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.protobuf.ProtobufJsonFormatHttpMessageConverter;

@Configuration
public class ServerServiceConfig {
	@Bean
	public ProtobufJsonFormatHttpMessageConverter protobufHttpMessageConverter() {
		return new ProtobufJsonFormatHttpMessageConverter();
	}

	@Bean
	public GrpcAuthenticationReader grpcAuthenticationReader() {
		return new BasicGrpcAuthenticationReader();
	}
}
