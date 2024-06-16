package com.techbellys.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PaymentClientRequest implements Serializable {
    private Long userId;
    private Double amount;
    private String method;
}