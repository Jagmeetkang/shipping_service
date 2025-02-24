package io.pragra.ecommerce.shipping_service.service;

import io.pragra.ecommerce.shipping_service.dto.OrderDetails;
import io.pragra.ecommerce.shipping_service.entity.Shipment;
import io.pragra.ecommerce.shipping_service.repository.ShipmentRepository;
import jakarta.mail.MessagingException;
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

    @Autowired
    private final EmailService emailService;

    public Shipment createShipment(OrderDetails orderDetails){
        Shipment shipment=new Shipment();
        shipment.setOrderId(orderDetails.getOrderId());
        shipment.setTrackingNumber(trackingNumberService());
        shipment.setStatus("Processing");
        shipment.setMailId(orderDetails.getEmail());
        shipment.setEstimatedDeliveryDate(LocalDate.now().plusDays(6));
        shipment.setShippingAddress(orderDetails.getShippingAddress());
        shipmentRepository.save(shipment);
        try {
            emailService.createOrderMail(shipment);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return shipment;
    }


    public Shipment statusUpdater(long shipmentId, String newStatus){
        Shipment shipment = shipmentRepository.findById(shipmentId)
                            .orElseThrow(()->new RuntimeException("Shipment not found"));
        shipment.setStatus(newStatus);
        shipmentRepository.save(shipment);
        try {
            emailService.statusUpdateMail(shipment);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return shipment;
    }

    public String trackingNumberService(){
        return UUID.randomUUID().toString();
    }
}
