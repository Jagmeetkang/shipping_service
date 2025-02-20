package io.pragra.ecommerce.shipping_service.service;

import io.pragra.ecommerce.shipping_service.dto.ShipmentRequest;
import io.pragra.ecommerce.shipping_service.entity.Shipment;
import io.pragra.ecommerce.shipping_service.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShippingService {

    @Autowired
    private final ShipmentRepository shipmentRepository;

    public Shipment createShipment(ShipmentRequest shipmentRequest){
        Shipment shipment=new Shipment();
        shipment.setOrderId(shipmentRequest.getOrderId());
        shipment.setTrackingNumber(UUID.randomUUID().toString());
        shipment.setStatus("Processing");
        shipment.setEstimatedDeliveryDate(LocalDate.now().plusDays(6));
        shipment.setShippingAddress(shipmentRequest.getShippingAddress());
        shipmentRepository.save(shipment);
        return shipment;
    }


    public Shipment statusUpdater(long shipmentId, String newStatus){
        Shipment shipment = shipmentRepository.findById(shipmentId)
                            .orElseThrow(()->new RuntimeException("Shipment not found"));

        shipment.setStatus(newStatus);
        shipmentRepository.save(shipment);
        return shipment;
    }
}
