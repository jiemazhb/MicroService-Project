package net.nvsoftware.internProductService.controller;

import lombok.extern.log4j.Log4j2;
import net.nvsoftware.internProductService.entity.ProductEntity;
import net.nvsoftware.internProductService.model.ProductRequest;
import net.nvsoftware.internProductService.model.ProductResponse;
import net.nvsoftware.internProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping()
    public ResponseEntity<Long> createProduct(@RequestBody ProductRequest productRequest){
        long productId = productService.createProduct(productRequest);
        return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") long productId){
        log.info("product 收到请求");
        ProductResponse productResponse = productService.getProductById(productId);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }
    @PutMapping("/reduceQuantity")
    public ResponseEntity<Void> reduceQuantity(@RequestParam long productId, @RequestParam long quantity){
        this.productService.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
