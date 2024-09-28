package com.cuscatlan.payments.infrastructure.external;

import com.cuscatlan.payments.application.dto.OrderDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign client interface for communicating with the Order Service.
 * This interface defines methods for retrieving and updating orders
 * through HTTP requests.
 */
@FeignClient(
        name = "order-service",
        url = "http://localhost:8082/api/v1/orders")
public interface OrderServiceClient {

    /**
     * Retrieves an order by its ID.
     *
     * @param id the ID of the order to retrieve
     * @return a ResponseEntity containing the OrderDto if found,
     *         or an appropriate HTTP error response if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id);

    /**
     * Updates an existing order.
     *
     * @param orderDto the OrderDto object containing updated order information
     * @return the updated OrderDto
     */
    @PutMapping()
    OrderDto updateOrder(@RequestBody OrderDto orderDto);
}
