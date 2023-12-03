package br.com.dianome.deliverymanagement.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dianome.deliverymanagement.dao.DeliveryRepository;
import br.com.dianome.deliverymanagement.dto.DeliveryDto;
import br.com.dianome.deliverymanagement.entity.Delivery;
import br.com.dianome.deliverymanagement.service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private DeliveryService deliveryService;

    @Autowired
    private DeliveryRepository deliveryRepository;

    private DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/create")
    public Delivery registerDelivery(@RequestBody DeliveryDto deliveryDAO) {
        System.out.println(deliveryDAO.toString());

        // DeliveryResponse deliveryResponse = deliveryService.registerDelivery(deliveryDAO);
        Delivery delivery = deliveryService.registerDelivery(deliveryDAO);

        return delivery;

    }

    @GetMapping("/")
    public ResponseEntity<List<Delivery>> listar() {
        List<Delivery> deliveries = deliveryRepository.findAllNonDeleted();
        return ResponseEntity.status(HttpStatus.OK).body(deliveries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detailDelivery(@PathVariable(value = "id") Long id) {

        Optional<Delivery> delivery = deliveryRepository.findById(id);

        if (delivery.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(delivery.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entrega não encontrada.");
        }

    }

    @PutMapping("/update")
    public Delivery updateDelivery(@RequestBody DeliveryDto deliveryDAO) {

        Delivery delivery = deliveryService.updateDelivery(deliveryDAO);

        return delivery;

    }


    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteDelivery(@RequestBody DeliveryDto deliveryDAO) {

        // deliveryService.deleteDelivery(deliveryDAO);

        return deliveryService.deleteDelivery(deliveryDAO);

    }

}
