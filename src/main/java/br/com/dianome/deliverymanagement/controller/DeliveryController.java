package br.com.dianome.deliverymanagement.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dianome.deliverymanagement.dto.DeliveryDto;
import br.com.dianome.deliverymanagement.entity.Delivery;
import br.com.dianome.deliverymanagement.service.DeliveryService;

@RestController
@RequestMapping("/api/deliveries")
public class DeliveryController {

    private DeliveryService deliveryService;

    private DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping("/")
    public ResponseEntity<Object> createDelivery(@RequestBody DeliveryDto deliveryDAO) {
        return deliveryService.createDelivery(deliveryDAO);
    }

    @GetMapping("/")
    public ResponseEntity<List<Delivery>> list() {
        return deliveryService.listDelivery();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> detailDelivery(@PathVariable(value = "id") Long id) {
        return deliveryService.detailDelivery(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDelivery(@PathVariable(value = "id") Long id,
            @RequestBody DeliveryDto deliveryDAO) {
        return deliveryService.updateDelivery(deliveryDAO, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDelivery(@PathVariable(value = "id") Long id,
            @RequestBody DeliveryDto deliveryDAO) {
        return deliveryService.deleteDelivery(deliveryDAO, id);
    }

}
