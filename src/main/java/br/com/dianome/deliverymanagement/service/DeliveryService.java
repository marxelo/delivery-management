package br.com.dianome.deliverymanagement.service;

import br.com.dianome.deliverymanagement.dto.DeliveryDto;
import br.com.dianome.deliverymanagement.dto.DeliveryResponse;

public interface DeliveryService {
    DeliveryResponse registerDelivery(DeliveryDto deliveryDto);

    DeliveryResponse updateDelivery(DeliveryDto deliveryDtO);

    DeliveryResponse deleteDelivery(DeliveryDto deliveryDtO);
}
