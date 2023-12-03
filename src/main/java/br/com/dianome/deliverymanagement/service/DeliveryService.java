package br.com.dianome.deliverymanagement.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.dianome.deliverymanagement.dto.DeliveryDto;
import br.com.dianome.deliverymanagement.entity.Delivery;

public interface DeliveryService {
    ResponseEntity<Object> createDelivery(DeliveryDto deliveryDto);
    
    ResponseEntity<Object> deleteDelivery(DeliveryDto deliveryDtO, Long id);

    ResponseEntity<Object> detailDelivery(Long id);    

    ResponseEntity<List<Delivery>> listDelivery();    

    ResponseEntity<Object> updateDelivery(DeliveryDto deliveryDtO, Long id);
}
