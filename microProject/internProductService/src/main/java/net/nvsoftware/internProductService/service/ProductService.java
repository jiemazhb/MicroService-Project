package net.nvsoftware.internProductService.service;

import net.nvsoftware.internProductService.model.ProductRequest;
import net.nvsoftware.internProductService.model.ProductResponse;

public interface ProductService {
    long createProduct(ProductRequest productRequest);

    ProductResponse getProductById(long productId);

    void reduceQuantity(long productId, long quantity);
}
