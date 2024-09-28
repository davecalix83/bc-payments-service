package com.cuscatlan.payments.infrastructure.external;

import com.cuscatlan.payments.application.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "order-service",
        url = "http://localhost:8082/api/v1/orders")
public interface OrderServiceClient {


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id);

    @PutMapping()
    OrderDto updateOrder(@RequestBody OrderDto orderDto);

}
