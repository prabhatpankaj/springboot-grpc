package com.techbellys.clientservice.service;

import com.techbellys.clientservice.component.PaymentMapper;
import com.techbellys.clientservice.dto.PaymentClientRequest;
import com.techbellys.clientservice.dto.PaymentClientResponse;
import com.techbellys.payment.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class PaymentClientService {

    @GrpcClient("PaymentService")
    private PaymentServiceGrpc.PaymentServiceBlockingStub paymentServiceBlockingStub;

    private final PaymentMapper paymentMapper;

    public PaymentClientResponse createPayment(PaymentClientRequest paymentClientRequest) {
        CreatePaymentRequest request = paymentMapper.toServerRequest(paymentClientRequest);
        PaymentResponse response = paymentServiceBlockingStub.createPayment(request);
        return paymentMapper.toClientResponse(response);
    }

    public PaymentClientResponse getPayment(GetPaymentRequest getPaymentRequest) {
        PaymentResponse response = paymentServiceBlockingStub.getPayment(getPaymentRequest);
        return paymentMapper.toClientResponse(response);
    }

    public List<PaymentClientResponse> getPaymentsByUserId(GetPaymentsByUserIdRequest getPaymentsByUserIdRequest) {
        PaymentsResponse response = paymentServiceBlockingStub.getPaymentsByUserId(getPaymentsByUserIdRequest);
        return paymentMapper.toClientResponseList(response);
    }

    public void deletePayment(DeletePaymentRequest deletePaymentRequest) {
        paymentServiceBlockingStub.deletePayment(deletePaymentRequest);
    }
}
