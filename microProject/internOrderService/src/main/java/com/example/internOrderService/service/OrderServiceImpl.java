package com.example.internOrderService.service;

import com.example.internOrderService.entiry.OrderEntity;
import com.example.internOrderService.external.client.PaymentServiceFeignClient;
import com.example.internOrderService.external.client.ProductServiceFeignClient;
import com.example.internOrderService.model.OrderRequest;
import com.example.internOrderService.model.OrderResponse;
import com.example.internOrderService.model.PaymentRequest;
import com.example.internOrderService.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.time.Instant;
import java.util.Optional;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductServiceFeignClient productServiceFeignClient;
    @Autowired
    private PaymentServiceFeignClient paymentServiceFeignClient;
    @Autowired
    private RestTemplate restTemplate;
    //在下面的方法中，搞懂如何进行数据库回滚，
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        try {
            OrderEntity orderEntity = OrderEntity.builder()
                    .productId(orderRequest.getProductId())
                    .orderQuantity(orderRequest.getOrderQuantity())
                    .totalAmount(orderRequest.getTotalAmount())
                    .orderDate(Instant.now())
                    .orderStatus("CREATED")
                    .paymentMode(orderRequest.getPaymentMode().name())
                    .build();
            orderEntity = this.orderRepository.save(orderEntity);

            this.productServiceFeignClient.reduceQuantity(orderRequest.getProductId(), orderRequest.getOrderQuantity());

            PaymentRequest paymentRequest = PaymentRequest.builder()
                    .orderId(orderEntity.getOrderId())
                    .paymentMode(orderRequest.getPaymentMode())
//                .referenceNum()
                    .totalAmount(orderEntity.getTotalAmount())
                    .build();
            this.paymentServiceFeignClient.makePayment(paymentRequest);

            return orderEntity.getOrderId();
        }catch (Exception e){
            log.info("exception happening.. placed order failed.");
            throw new RuntimeException("failed to place order", e);
        }
    }

    @Override
    public OrderResponse getOrderDetailByOrderId(long orderId) {
        try {
            OrderEntity orderEntity = this.orderRepository.findById(orderId)
                    .orElseThrow(()->new RuntimeException("order was not found for orderId :" + orderId));


            OrderResponse.ProductResponse productResponse = this.restTemplate.getForObject(
                    "http://PRODUCT-SERVICE/products/"+ orderEntity.getProductId(), OrderResponse.ProductResponse.class);

            OrderResponse.PaymentResponse paymentResponse = this.restTemplate.getForObject(
                    "http://PAYMENT-SERVICE/payments/"+ orderEntity.getOrderId(), OrderResponse.PaymentResponse.class);

            OrderResponse orderResponse = OrderResponse.builder()
                    .orderId(orderEntity.getOrderId())
                    .totalAmount(orderEntity.getTotalAmount())
                    .orderDate(orderEntity.getOrderDate())
                    .orderStatus(orderEntity.getOrderStatus())
                    .productResponse(productResponse)
                    .paymentResponse(paymentResponse)
                    .build();

            log.info("successfully get the order details");
            return orderResponse;
        }catch (Exception e){
            throw new RuntimeException("can not find the record by the orderId" + orderId);
        }
    }
}
