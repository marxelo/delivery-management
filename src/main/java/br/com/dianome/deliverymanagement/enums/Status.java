package br.com.dianome.deliverymanagement.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Status {
    CREATED("Registrada"),
    WAIT_PICKUP("Aguardando Retirada pelo Entregador"),
    IN_TRANSIT("Em trânsito"),
    DELIVERED("Entregue"),
    RETURNED("Devolvida"),
    CANCELED("Cancelada"),
    DELETED("Deletada");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}

