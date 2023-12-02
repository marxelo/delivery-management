package br.com.dianome.deliverymanagement.dto;

import java.util.Set;

import br.com.dianome.deliverymanagement.entity.Delivery;
import br.com.dianome.deliverymanagement.entity.DeliveryEvent;
import br.com.dianome.deliverymanagement.entity.Person;
import lombok.Data;

@Data
public class DeliveryData {
    
    private Delivery delivery;
    private Person costumer;
    private Person delivery_Person;
    private Set<DeliveryEvent> events;
}
