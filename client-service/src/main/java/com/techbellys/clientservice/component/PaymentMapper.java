package com.techbellys.clientservice.component;

import com.techbellys.clientservice.dto.PaymentClientRequest;
import com.techbellys.clientservice.dto.PaymentClientResponse;
import com.techbellys.payment.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaymentMapper {

    public CreatePaymentRequest toServerRequest(PaymentClientRequest paymentClientRequest) {
        return CreatePaymentRequest.newBuilder()
                .setUserId(paymentClientRequest.getUserId())
                .setAmount(paymentClientRequest.getAmount())
                .setMethod(paymentClientRequest.getMethod())
                .build();
    }

    public PaymentClientResponse toClientResponse(PaymentResponse paymentResponse) {
        return PaymentClientResponse.builder()
                .paymentId(paymentResponse.getPaymentId())
                .userId(paymentResponse.getUserId())
                .amount(paymentResponse.getAmount())
                .method(paymentResponse.getMethod())
                .paymentDate(paymentResponse.getPaymentDate())
                .build();
    }

    public List<PaymentClientResponse> toClientResponseList(PaymentsResponse paymentsResponse) {
        return paymentsResponse.getPaymentsList().stream()
                .map(this::toClientResponse)
                .collect(Collectors.toList());
    }
}
