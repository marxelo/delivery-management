package br.com.dianome.deliverymanagement.dto;

import br.com.dianome.deliverymanagement.enums.Action;
import lombok.Data;

@Data
public class DeliveryDto {

    Action action;

    private Long itemId;

    private Long costumerId;

    private Long deliveryPersonId;

    private String note;

}
