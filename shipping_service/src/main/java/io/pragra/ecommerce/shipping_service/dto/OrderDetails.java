package io.pragra.ecommerce.shipping_service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetails {
    private String orderId;
    private String shippingAddress;
    private String email;
}
