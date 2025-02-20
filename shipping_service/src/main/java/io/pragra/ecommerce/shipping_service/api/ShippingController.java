package io.pragra.ecommerce.shipping_service.api;

import io.pragra.ecommerce.shipping_service.dto.ShipmentRequest;
import io.pragra.ecommerce.shipping_service.entity.Shipment;
import io.pragra.ecommerce.shipping_service.repository.ShipmentRepository;
import io.pragra.ecommerce.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipping")
@RequiredArgsConstructor
public class ShippingController {

    @Autowired
    private final ShippingService shippingService;

    @PostMapping("/create")
    public ResponseEntity<Shipment> CreateShipment(@RequestBody ShipmentRequest shipmentRequest){
       Shipment shipment=shippingService.createShipment(shipmentRequest);
       return new ResponseEntity<>(shipment, HttpStatus.CREATED);
    }
}
