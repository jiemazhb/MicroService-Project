package net.nvsoftware.internProductService.service;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.internProductService.entity.ProductEntity;
import net.nvsoftware.internProductService.model.OrderEvent;
import net.nvsoftware.internProductService.model.ProductRequest;
import net.nvsoftware.internProductService.model.ProductResponse;
import net.nvsoftware.internProductService.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Override
    public long createProduct(ProductRequest productRequest) {
        log.info("start create product");
        ProductEntity productEntity = ProductEntity.builder()
                .productName(productRequest.getProductName())
                .productPrice(productRequest.getProductPrice())
                .productQuantity(productRequest.getProductQuantity()).build();
        this.productRepository.save(productEntity);
        log.info("product object create successfully with productId: "+ productEntity.getProductId());
        return productEntity.getProductId();
    }

    @Override
    public ProductResponse getProductById(long productId) {
        ProductEntity productEntity = this.productRepository.findById(productId)
                .orElseThrow(()->new RuntimeException("record not found!"));
        ProductResponse productResponse = new ProductResponse();
        BeanUtils.copyProperties(productEntity,productResponse);
        return productResponse;
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        ProductEntity productEntity = this.productRepository.findById(productId).orElseThrow(()-> new RuntimeException("not found"));
        if (productEntity.getProductQuantity() < quantity){
            throw new RuntimeException("quantity is not enought");
        }
        productEntity.setProductQuantity(productEntity.getProductQuantity() - quantity);
        this.productRepository.save(productEntity);
    }
    @KafkaListener(topics = "order", groupId = "product")
    public void reduceQuantityFromKafkaMQ(OrderEvent orderEvent) {

        long productId = orderEvent.getProductId();
        long quantity = orderEvent.getQuantity();


        ProductEntity productEntity = this.productRepository.findById(productId).orElseThrow(()-> new RuntimeException("not found"));
        if (productEntity.getProductQuantity() < quantity){
            throw new RuntimeException("quantity is not enought");
        }
        productEntity.setProductQuantity(productEntity.getProductQuantity() - quantity);
        this.productRepository.save(productEntity);
    }
}
