package com.techbellys.serverservice.service.impl;

import com.techbellys.payment.*;
import com.techbellys.serverservice.model.Payment;
import com.techbellys.serverservice.service.PaymentServerService;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@GrpcService
@Slf4j
@AllArgsConstructor
public class PaymentGrpcServiceImpl extends PaymentServiceGrpc.PaymentServiceImplBase {

    @Autowired
    private PaymentServerService paymentServerService;

    @Override
    public void createPayment(CreatePaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        Payment payment = paymentServerService.createPayment(request.getUserId(), request.getAmount(), request.getMethod());
        PaymentResponse response = PaymentResponse.newBuilder()
                .setPaymentId(payment.getId().toString())
                .setUserId(payment.getUserId())
                .setAmount(payment.getAmount())
                .setMethod(payment.getMethod())
                .setPaymentDate(payment.getPaymentDate().toString())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getPayment(GetPaymentRequest request, StreamObserver<PaymentResponse> responseObserver) {
        Payment payment = paymentServerService.getPaymentById(Long.parseLong(request.getPaymentId()));
        if (payment != null) {
            PaymentResponse response = PaymentResponse.newBuilder()
                    .setPaymentId(payment.getId().toString())
                    .setUserId(payment.getUserId())
                    .setAmount(payment.getAmount())
                    .setMethod(payment.getMethod())
                    .setPaymentDate(payment.getPaymentDate().toString())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(new IllegalArgumentException("Payment not found"));
        }
    }

    @Override
    public void getPaymentsByUserId(GetPaymentsByUserIdRequest request, StreamObserver<PaymentsResponse> responseObserver) {
        List<Payment> payments = paymentServerService.getPaymentsByUserId(request.getUserId());
        PaymentsResponse.Builder responseBuilder = PaymentsResponse.newBuilder();
        for (Payment payment : payments) {
            PaymentResponse paymentResponse = PaymentResponse.newBuilder()
                    .setPaymentId(payment.getId().toString())
                    .setUserId(payment.getUserId())
                    .setAmount(payment.getAmount())
                    .setMethod(payment.getMethod())
                    .setPaymentDate(payment.getPaymentDate().toString())
                    .build();
            responseBuilder.addPayments(paymentResponse);
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void deletePayment(DeletePaymentRequest request, StreamObserver<EmptyResponse> responseObserver) {
        paymentServerService.deletePayment(Long.parseLong(request.getPaymentId()));
        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }
}
