package com.saree.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StockReservationRequest {

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    public StockReservationRequest() {
    }

    public StockReservationRequest(String sku, Integer quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sku;
        private Integer quantity;

        public Builder sku(String sku) { this.sku = sku; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }

        public StockReservationRequest build() {
            return new StockReservationRequest(sku, quantity);
        }
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
