package br.com.dianome.deliverymanagement.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.dianome.deliverymanagement.entity.Person;

@RepositoryRestResource
public interface PersonRepository extends JpaRepository<Person, Long> {

}
