package com.srhdp.OrderService.service;

import com.srhdp.OrderService.entity.Order;
import com.srhdp.OrderService.exception.CustomException;
import com.srhdp.OrderService.external.client.PaymentService;
import com.srhdp.OrderService.external.client.ProductService;
import com.srhdp.OrderService.external.request.PaymentRequest;
import com.srhdp.OrderService.external.response.PaymentResponse;
import com.srhdp.OrderService.model.OrderRequest;
import com.srhdp.OrderService.model.OrderResponse;
import com.srhdp.OrderService.model.PaymentMode;
import com.srhdp.OrderService.repository.OrderRepository;
import com.srhdp.ProductService.model.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductService productService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();
    @DisplayName("Get Order - Success Scenario")
    @Test
    void test_When_Order_Success() {
        // Mocking
        Order order = getMockOrder();
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        when(restTemplate.getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                ProductResponse.class
        )).thenReturn(getMockProductResponse());

        when(restTemplate.getForObject(
                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
                PaymentResponse.class
        )).thenReturn(getMockPaymentResponse());


        long orderId = 1;
        //Actual
        OrderResponse orderResponse = orderService.getOrderDetails(orderId);

        //Verification
        verify(orderRepository, times(1)).findById(anyLong());
        verify(restTemplate, times(1)).getForObject(
                "http://PRODUCT-SERVICE/product/" + order.getProductId(),
                ProductResponse.class);
        verify(restTemplate, times(1)).getForObject(
                "http://PAYMENT-SERVICE/payment/order/" + order.getId(),
                PaymentResponse.class);

        //Assert
        assertNotNull(orderResponse);
        assertEquals(order.getId(), orderResponse.getOrderId());


    }

    private PaymentResponse getMockPaymentResponse() {
        return PaymentResponse.builder()
                .paymentId(1)
                .paymentDate(Instant.now())
                .paymentMode(PaymentMode.CASH)
                .amount(200)
                .orderId(1)
                .status("ACCEPTED")
                .build();
    }

    private ProductResponse getMockProductResponse() {
        return ProductResponse.builder()
                .productId(2)
                .productName("iPhone")
                .price(100)
                .quantity(200)
                .build();
    }

    private Order getMockOrder() {
        return Order.builder()
                .orderStatus("PLACED")
                .orderDate(Instant.now())
                .id(1)
                .amount(100)
                .quantity(200)
                .productId(2)
                .build();
    }

}