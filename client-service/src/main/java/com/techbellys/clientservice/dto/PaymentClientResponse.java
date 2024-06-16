package com.techbellys.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentClientResponse implements Serializable {
    private String paymentId;
    private Long userId;
    private Double amount;
    private String method;
    private String paymentDate;
}
