package br.com.dianome.deliverymanagement.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import br.com.dianome.deliverymanagement.enums.BooleanValue;
import br.com.dianome.deliverymanagement.enums.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "delivery")
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_code")
    private String trackingCode;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id", referencedColumnName = "id", nullable = true)
    private Item item;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "costumer_id", referencedColumnName = "id", nullable = true)
    private Person costumer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_person_id", referencedColumnName = "id", nullable = true)
    private Person deliveryPerson;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    @Column(name = "status")
    private Status status;

    @Column(name = "note")
    private String note;

    @Column(name = "is_deleted")
    @JsonIgnore
    private BooleanValue isDeleted;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "delivery")
    @JsonManagedReference
    private List<DeliveryEvent> events;


    public void add(DeliveryEvent event) {
        if (events == null) {
            events =  new ArrayList<>();
        }
        events.add(event);
        event.setDelivery(this);
    }
}
