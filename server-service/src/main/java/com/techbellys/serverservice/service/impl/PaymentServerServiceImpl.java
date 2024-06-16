package com.techbellys.serverservice.service.impl;

import com.techbellys.serverservice.model.Payment;
import com.techbellys.serverservice.repository.PaymentRepository;
import com.techbellys.serverservice.service.PaymentServerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentServerServiceImpl implements PaymentServerService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment createPayment(Long userId, Double amount, String method) {
        Payment payment = new Payment();
        payment.setUserId(userId);
        payment.setAmount(amount);
        payment.setMethod(method);
        payment.setPaymentDate(new Date());
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElse(null);
    }
    @Override
    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }
    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
