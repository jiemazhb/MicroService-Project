package com.example.internOrderService.service;

import com.example.internOrderService.entiry.OrderEntity;
import com.example.internOrderService.external.client.PaymentServiceFeignClient;
import com.example.internOrderService.external.client.ProductServiceFeignClient;
import com.example.internOrderService.model.OrderRequest;
import com.example.internOrderService.model.OrderResponse;
import com.example.internOrderService.model.PaymentMode;
import com.example.internOrderService.model.PaymentRequest;
import com.example.internOrderService.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

@SpringBootTest
class OrderServiceImplTest {

    @Mock
    RestTemplate restTemplate;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductServiceFeignClient productServiceFeignClient;
    @Mock
    private PaymentServiceFeignClient paymentServiceFeignClient;
    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @DisplayName("Get Order Detail - Success")
    @Test
    void testWhenGetOrderSuccess(){
        // mock part
        OrderEntity orderEntity = getMockOrderEntity();
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(orderEntity));
        Mockito.when(restTemplate.getForObject("http://PRODUCT-SERVICE/products/" + orderEntity.getProductId(), OrderResponse.ProductResponse.class))
                .thenReturn(getMockProductResponse());
        Mockito.when(restTemplate.getForObject("http://PAYMENT-SERVICE/payments/" + orderEntity.getOrderId(), OrderResponse.PaymentResponse.class))
                        .thenReturn(getMockPaymentResponse());
        // actual call
        OrderResponse orderResponse = orderService.getOrderDetailByOrderId(1);
        // verify call
        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("http://PRODUCT-SERVICE/products/" + orderEntity.getProductId(), OrderResponse.ProductResponse.class);
        Mockito.verify(restTemplate, Mockito.times(1)).getForObject("http://PAYMENT-SERVICE/payments/" + orderEntity.getOrderId(), OrderResponse.PaymentResponse.class);
        // assert response
        Assertions.assertNotNull(orderResponse);
        Assertions.assertEquals(orderEntity.getOrderId(), orderResponse.getOrderId());
    }
    @Test
    @DisplayName("Get Order Detail orderId Not found - FAILED")
    void testWhenOrderIdNotFound(){
        Mockito.when(orderRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));

        RuntimeException runtimeException = Assertions.assertThrows(RuntimeException.class, () -> orderService.getOrderDetailByOrderId(1));

        Assertions.assertEquals("can not find the record by the orderId1", runtimeException.getMessage());

        Mockito.verify(orderRepository, Mockito.times(1)).findById(Mockito.anyLong());

    }
    @Test
    @DisplayName("place order - success")
    void testWhenPlaceOrderSuccess(){
        OrderEntity orderEntity = getMockOrderEntity();
        OrderRequest orderRequest = getMockOrderRequest();

        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class))).thenReturn(orderEntity);
        Mockito.when(productServiceFeignClient.reduceQuantity(Mockito.anyLong(), Mockito.anyLong()))
                .thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        Mockito.when(paymentServiceFeignClient.makePayment(Mockito.any(PaymentRequest.class)))
                .thenReturn(new ResponseEntity<Long>(1L,HttpStatus.OK));
        long orderId = orderService.placeOrder(orderRequest);
        Mockito.verify(orderRepository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(productServiceFeignClient, Mockito.times(1)).reduceQuantity(Mockito.anyLong(), Mockito.anyLong());
        Mockito.verify(paymentServiceFeignClient, Mockito.times(1)).makePayment(Mockito.any(PaymentRequest.class));
    }
    @Test
    @DisplayName("place order - Failed")
    void testWhenPlaceOrderFailed(){
//        OrderRequest orderRequest = getMockOrderRequest();
//
//        OrderEntity orderEntity = getMockOrderEntity();
//
//        Mockito.when(orderRepository.save(Mockito.any(OrderEntity.class))).thenReturn(orderEntity);
//
//        // 模拟 productServiceFeignClient.reduceQuantity 方法抛出 "quantity is not enough" 异常
//        Mockito.doThrow(new RuntimeException("quantity is not enough"))
//                .when(productServiceFeignClient).reduceQuantity(Mockito.anyLong(), Mockito.anyInt());
//
//        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
//            orderService.placeOrder(orderRequest);
//        });
//
//        String expectedMessage = "failed to place order";
//        String actualMessage = exception.getMessage();
//
//        assertTrue(actualMessage.contains(expectedMessage));
    }
    private OrderRequest getMockOrderRequest() {
        return OrderRequest.builder()
                .productId(1).orderQuantity(100).totalAmount(34).paymentMode(PaymentMode.CASH).build();

    }

    private OrderEntity getMockOrderEntity() {
        return OrderEntity.builder()
                .orderId(1)
                .productId(1)
                .orderQuantity(1)
                .totalAmount(1)
                .orderDate(Instant.now())
                .orderStatus("placed")
                .build();
    }
    private OrderResponse.PaymentResponse getMockPaymentResponse(){
        return OrderResponse.PaymentResponse.builder()
                .orderId(1).paymentId(1).paymentDate(Instant.now())
                .paymentMode(PaymentMode.CASH).paymentStatus("SUCCESS")
                .totalAmount(543).build();
    }
    private OrderResponse.ProductResponse getMockProductResponse(){
        return OrderResponse.ProductResponse.builder().productId(5).productName("iphone")
                .productQuantity(2).productPrice(234).build();
    }
}