package br.com.dianome.deliverymanagement.service;

import br.com.dianome.deliverymanagement.dto.DeliveryResponse;
import br.com.dianome.deliverymanagement.entity.Delivery;

public interface DeliveryService {
    DeliveryResponse registerDelivery(Delivery delivery);
}
