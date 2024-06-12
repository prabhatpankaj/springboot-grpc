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
public class ProductClientResponse implements Serializable {
    private String productId;
    private String name;
    private float price;
    private String categoryId;
}
