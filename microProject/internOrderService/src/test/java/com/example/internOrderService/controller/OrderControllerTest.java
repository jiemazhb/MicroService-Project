package com.example.internOrderService.controller;

import com.example.internOrderService.OrderServiceConfigTest;
import com.example.internOrderService.model.OrderRequest;
import com.example.internOrderService.repository.OrderRepository;
import com.example.internOrderService.service.OrderService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;


@SpringBootTest
@EnableConfigurationProperties
@AutoConfigureMockMvc
@ContextConfiguration(classes = {OrderServiceConfigTest.class})
public class OrderControllerTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private MockMvc mockMvc;
    static WireMockExtension wireMockExtension = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig().port(8053))
            .build();

    @BeforeEach
    void setupTest() throws IOException {
        reduceQuantity();
        getProductResponse();
        makePayment();
        getPaymentResponse();
    }

    private void getPaymentResponse() throws IOException {
        wireMockExtension.stubFor(WireMock.get("/payments/1")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(StreamUtils.copyToString(OrderControllerTest.class
                                .getClassLoader().getResourceAsStream("mockData/paymentResponse.json"), Charset.defaultCharset())

                        )
                )
        );
    }

    private void makePayment() {
        wireMockExtension.stubFor(WireMock.post("/payments")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }

    private void getProductResponse() throws IOException {
        wireMockExtension.stubFor(WireMock.get("/products/1")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(StreamUtils.copyToString(OrderControllerTest.class
                                .getClassLoader().getResourceAsStream("mockData/productResponse.json"), Charset.defaultCharset())

                        )
                )
        );
    }

    private void reduceQuantity() {
        wireMockExtension.stubFor(WireMock.put("/product/reduceQuantity")
                .willReturn(WireMock.aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                )
        );
    }

    @Test
    public void testPlaceOrderMakePaymentSuccess(){ // TODO: finish JWT token for sevices
           // Place Order

            // get order from DB by orderId

            // verify orderResponse
    }

}
