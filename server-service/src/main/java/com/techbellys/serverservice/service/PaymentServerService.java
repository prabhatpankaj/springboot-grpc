package com.techbellys.serverservice.service;

import com.techbellys.serverservice.model.Payment;
import java.util.List;

public interface PaymentServerService {

    Payment createPayment(Long userId, Double amount, String method);

    Payment getPaymentById(Long id);

    List<Payment> getPaymentsByUserId(Long userId);

    void deletePayment(Long id);

    List<Payment> getAllPayments();
}
