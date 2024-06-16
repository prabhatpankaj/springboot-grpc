package com.techbellys.clientservice.controller;


import com.techbellys.clientservice.dto.PaymentClientRequest;
import com.techbellys.clientservice.dto.PaymentClientResponse;
import com.techbellys.clientservice.service.PaymentClientService;
import com.techbellys.payment.DeletePaymentRequest;
import com.techbellys.payment.GetPaymentRequest;
import com.techbellys.payment.GetPaymentsByUserIdRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@AllArgsConstructor
@Slf4j
public class PaymentController {

    private final PaymentClientService paymentClientService;

    @PreAuthorize("hasAnyRole('client')")
    @PostMapping
    public ResponseEntity<PaymentClientResponse> createPayment(@RequestBody PaymentClientRequest paymentClientRequest) {
        PaymentClientResponse response = paymentClientService.createPayment(paymentClientRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentClientResponse> getPayment(@PathVariable String paymentId) {
        GetPaymentRequest request = GetPaymentRequest.newBuilder().setPaymentId(paymentId).build();
        PaymentClientResponse response = paymentClientService.getPayment(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PaymentClientResponse>> getPaymentsByUserId(@PathVariable Long userId) {
        GetPaymentsByUserIdRequest request = GetPaymentsByUserIdRequest.newBuilder().setUserId(userId).build();
        List<PaymentClientResponse> responses = paymentClientService.getPaymentsByUserId(request);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity<Void> deletePayment(@PathVariable String paymentId) {
        DeletePaymentRequest request = DeletePaymentRequest.newBuilder().setPaymentId(paymentId).build();
        paymentClientService.deletePayment(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
