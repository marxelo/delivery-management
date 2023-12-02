package br.com.dianome.deliverymanagement.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.dianome.deliverymanagement.dao.DeliveryRepository;
import br.com.dianome.deliverymanagement.dto.DeliveryResponse;
import br.com.dianome.deliverymanagement.entity.Delivery;
import jakarta.transaction.Transactional;

@Service
public class DeliveyServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;

    public DeliveyServiceImpl(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    @Override
    @Transactional
    public DeliveryResponse registerDelivery(Delivery delivery) {


        delivery.setTrackingCode(generateTrackingCode());

        // DeliveryEvent event = new DeliveryEvent(Status.CREATED, Curr);
        deliveryRepository.save(delivery);

        return new DeliveryResponse("sfasfa-sfaf");
    }


    private String generateTrackingCode() {

        // generate a random UUID number (UUID version-4)
        // For Details see: en.wikipedia.org/wiki/Universally_unique_identifier
        return UUID.randomUUID().toString();
    }
}
