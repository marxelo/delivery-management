package br.com.dianome.deliverymanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.dianome.deliverymanagement.entity.Delivery;


@RepositoryRestResource
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

//  isDeleted = 0 -> false (not deleted)

    @Query("select r, e from Delivery r join r.events e where r.isDeleted = 0 ")
    List<Delivery> findAllNonDeleted();

}
