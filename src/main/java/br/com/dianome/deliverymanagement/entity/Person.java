package br.com.dianome.deliverymanagement.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.dianome.deliverymanagement.enums.BooleanValue;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "Person")
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "officialId")
    private Long officialId;

    @Column(name = "address")
    private String address;

    @Column(name = "isDeliveryPerson")
    @JsonIgnore
    private BooleanValue isDeliveryPerson;
    
}
