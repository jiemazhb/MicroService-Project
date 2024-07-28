package net.nvsoftware.internProductService.model;

import lombok.Data;

@Data
public class ProductRequest {
    private String productName;
    private long productPrice;
    private long productQuantity;
}