package br.com.dianome.deliverymanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.dianome.deliverymanagement.dao.DeliveryRepository;
import br.com.dianome.deliverymanagement.dao.ItemRepository;
import br.com.dianome.deliverymanagement.dao.PersonRepository;
import br.com.dianome.deliverymanagement.dto.DeliveryDto;
import br.com.dianome.deliverymanagement.entity.Delivery;
import br.com.dianome.deliverymanagement.entity.DeliveryEvent;
import br.com.dianome.deliverymanagement.entity.Item;
import br.com.dianome.deliverymanagement.entity.Person;
import br.com.dianome.deliverymanagement.enums.Action;
import br.com.dianome.deliverymanagement.enums.BooleanValue;
import br.com.dianome.deliverymanagement.enums.Status;
import jakarta.transaction.Transactional;

@Service
public class DeliveyServiceImpl implements DeliveryService {

    private DeliveryRepository deliveryRepository;

    private PersonRepository personRepository;

    private ItemRepository orderRepository;

    public DeliveyServiceImpl(DeliveryRepository deliveryRepository, PersonRepository personRepository,
            ItemRepository orderRepository) {
        this.deliveryRepository = deliveryRepository;
        this.personRepository = personRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public ResponseEntity<Object> createDelivery(DeliveryDto deliveryDto) {

        Delivery delivery = new Delivery();

        delivery.setTrackingCode(generateTrackingCode());

        Optional<Person> costumer = personRepository.findById(deliveryDto.getCostumerId());

        if (costumer.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cliente informado não cadastrado.");
        }

        Optional<Item> item = orderRepository.findById(deliveryDto.getItemId());

        if (item.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ordem informada não cadastrada.");
        }

        delivery.setCostumer(costumer.get());
        delivery.setStatus(Status.CREATED);

        LocalDateTime localDateTime = LocalDateTime.now();
        delivery.setDateCreated(localDateTime);
        delivery.setLastUpdated(localDateTime);
        delivery.setNote(deliveryDto.getNote());
        delivery.setIsDeleted(BooleanValue.FALSE);
        delivery.setItem(item.get());

        DeliveryEvent deliveryEvent = createDeliveryEvent(deliveryDto, delivery);

        delivery.add(deliveryEvent);
        deliveryRepository.save(delivery);

        return ResponseEntity.status(HttpStatus.CREATED).body(delivery);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> updateDelivery(DeliveryDto deliveryDto, Long id) {

        Optional<Delivery> deliveryOptional = deliveryRepository.findById(id);

        if (deliveryOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entrega não encontrada para edição.");
        }

        Delivery delivery = deliveryOptional.get();

        Action action = deliveryDto.getAction();

        switch (action) {
            case REGISTER_DELIVERY_PERSON:

                Optional<Person> deliveryPerson = personRepository.findById(deliveryDto.getDeliveryPersonId());

                if (deliveryPerson.isEmpty() || deliveryPerson.get().getIsDeliveryPerson() == BooleanValue.FALSE) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Entregador informado não cadastrado.");
                }

                delivery.setDeliveryPerson(deliveryPerson.get());
                delivery.setStatus(Status.WAIT_PICKUP);
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

        return ResponseEntity.status(HttpStatus.OK).body(delivery);
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
    public ResponseEntity<Object> deleteDelivery(DeliveryDto deliveryDto, Long id) {
        deliveryDto.setAction(Action.DELETE);

        ResponseEntity<Object> response = updateDelivery(deliveryDto, id);

        if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entrega não encontrada para exclusão.");
        }

        return ResponseEntity.status(HttpStatus.OK).body("Entrega excluída.");
    }

    @Override
    @Transactional
    public ResponseEntity<Object> detailDelivery(Long id) {
        Optional<Delivery> delivery = deliveryRepository.findById(id);

        if (delivery.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(delivery.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entrega não encontrada.X");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<List<Delivery>> listDelivery() {
        List<Delivery> deliveries = deliveryRepository.findAllNonDeleted();
        return ResponseEntity.status(HttpStatus.OK).body(deliveries);
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
