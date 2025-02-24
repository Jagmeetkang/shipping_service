package io.pragra.ecommerce.shipping_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.pragra.ecommerce.shipping_service.dto.OrderDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaOrderConsumer {
    @Autowired
    private final ShippingService shippingService;

    @KafkaListener(topics = "shipping-details",groupId = "shipping-group")
    public void consume(@Payload String orderInfo) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        OrderDetails orderDetails = objectMapper.readValue(orderInfo, OrderDetails.class);
        System.out.println(orderInfo);
        shippingService.createShipment(orderDetails);
    }
}
