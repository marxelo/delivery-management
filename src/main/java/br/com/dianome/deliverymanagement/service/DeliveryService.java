package br.com.dianome.deliverymanagement.service;

import org.springframework.http.ResponseEntity;

import br.com.dianome.deliverymanagement.dto.DeliveryDto;
import br.com.dianome.deliverymanagement.entity.Delivery;

public interface DeliveryService {
    Delivery registerDelivery(DeliveryDto deliveryDto);

    Delivery updateDelivery(DeliveryDto deliveryDtO);

    ResponseEntity<Object>  deleteDelivery(DeliveryDto deliveryDtO);
}
