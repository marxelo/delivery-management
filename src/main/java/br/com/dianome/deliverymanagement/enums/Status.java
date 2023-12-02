package br.com.dianome.deliverymanagement.enums;

public enum Status {
    CREATED("Registrada"),
    SHIPPED("Despachada"),
    IN_TRANSIT("Em trânsito"),
    DELIVERED("Entregue"),
    RETURNED("Devolvida"),
    CANCELED("Cancelada");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

