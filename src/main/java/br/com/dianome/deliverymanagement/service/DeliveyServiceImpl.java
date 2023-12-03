package br.com.dianome.deliverymanagement.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.dianome.deliverymanagement.dao.DeliveryRepository;
import br.com.dianome.deliverymanagement.dao.PersonRepository;
import br.com.dianome.deliverymanagement.dto.DeliveryDto;
import br.com.dianome.deliverymanagement.dto.DeliveryResponse;
import br.com.dianome.deliverymanagement.entity.Delivery;
import br.com.dianome.deliverymanagement.entity.DeliveryEvent;
import br.com.dianome.deliverymanagement.entity.Person;
import br.com.dianome.deliverymanagement.enums.Action;
import br.com.dianome.deliverymanagement.enums.BooleanValue;
import br.com.dianome.deliverymanagement.enums.Status;
import jakarta.transaction.Transactional;

@Service
public class DeliveyServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;


    private PersonRepository personRepository;

    public DeliveyServiceImpl(DeliveryRepository deliveryRepository, PersonRepository personRepository) {
        this.deliveryRepository = deliveryRepository;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public DeliveryResponse registerDelivery(DeliveryDto deliveryDto) {

        Delivery delivery = new Delivery();

        delivery.setTrackingCode(generateTrackingCode());

        Optional<Person> costumer = personRepository.findById(deliveryDto.getCostumerId());

        delivery.setCostumer(costumer.get());
        delivery.setStatus(Status.CREATED);

        delivery.setDateCreated(LocalDateTime.now());
        delivery.setLastUpdated(LocalDateTime.now());
        delivery.setNote(deliveryDto.getNote());
        delivery.setIsDeleted(BooleanValue.FALSE);

        DeliveryEvent deliveryEvent = createDeliveryEvent(deliveryDto, delivery);

        delivery.add(deliveryEvent);
        deliveryRepository.save(delivery);
        
        return new DeliveryResponse(delivery.getTrackingCode());
    }

    @Override
    @Transactional
    public DeliveryResponse updateDelivery(DeliveryDto deliveryDto) {

        Delivery delivery = deliveryRepository.findByTrackingCode(deliveryDto.getTrackingCode());

        Action action = deliveryDto.getAction();

        System.out.println("\n\n\ndeliveryId: " + delivery.getId() + "\n\n\n");
        switch (action) {
            case REGISTER_DELIVERY_PERSON:

                Optional<Person> deliveryPerson = personRepository.findById(deliveryDto.getDeliveryPersonId());

                delivery.setDeliveryPerson(deliveryPerson.get());
                delivery.setStatus(Status.SHIPPED);
                handleNoteUpdate(deliveryDto, delivery);

                break;
            case REGISTER_IN_TRANSIT:
                handleNoteUpdate(deliveryDto, delivery);
                delivery.setStatus(Status.IN_TRANSIT);
                break;
            case DELIVER:
                handleNoteUpdate(deliveryDto, delivery);
                delivery.setStatus(Status.DELIVERED);
                break;
            case RETURN:
                handleNoteUpdate(deliveryDto, delivery);
                delivery.setStatus(Status.RETURNED);
                break;
            case CANCEL:
                handleNoteUpdate(deliveryDto, delivery);
                delivery.setStatus(Status.CANCELED);
                break;
            case DELETE:
                handleNoteUpdate(deliveryDto, delivery);
                delivery.setIsDeleted(BooleanValue.TRUE);
                delivery.setStatus(Status.DELETED);
                break;
            default:
                break;
        }

        delivery.setLastUpdated(LocalDateTime.now());
        DeliveryEvent deliveryEvent = createDeliveryEvent(deliveryDto, delivery);
        delivery.add(deliveryEvent);

        return new DeliveryResponse(delivery.getTrackingCode());
    }

    private DeliveryEvent createDeliveryEvent(DeliveryDto deliveryDto, Delivery delivery) {
        DeliveryEvent deliveryEvent = new DeliveryEvent();

        deliveryEvent.setDelivery(delivery);
        deliveryEvent.setNote(deliveryDto.getNote());
        deliveryEvent.setStatus(delivery.getStatus());
        deliveryEvent.setEventTimestamp(delivery.getLastUpdated());
        return deliveryEvent;
    }

    @Override
    @Transactional
    public DeliveryResponse deleteDelivery(DeliveryDto deliveryDto) {
        deliveryDto.setAction(Action.DELETE);

        updateDelivery(deliveryDto);

        return new DeliveryResponse(deliveryDto.getTrackingCode());
    }

    private void handleNoteUpdate(DeliveryDto deliveryDto, Delivery delivery) {
        if (!deliveryDto.getNote().isEmpty()) {
            delivery.setNote(deliveryDto.getNote());
        }
    }

    private String generateTrackingCode() {

        // generate a random UUID number (UUID version-4)
        // For Details see: en.wikipedia.org/wiki/Universally_unique_identifier
        return UUID.randomUUID().toString();
    }
}
