package br.com.dianome.deliverymanagement.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.dianome.deliverymanagement.entity.DeliveryEvent;

@RepositoryRestResource
public interface DeliveryEventRepository  extends JpaRepository<DeliveryEvent, Long> {

    List<DeliveryEvent> findByDeliveryId(Long id);
    
}
