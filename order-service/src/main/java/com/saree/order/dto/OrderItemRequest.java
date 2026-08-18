package com.saree.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemRequest {

    private Long sareeId;

    @NotBlank(message = "Saree name is mandatory")
    private String sareeName;

    @NotBlank(message = "SKU is mandatory")
    private String sku;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price is mandatory")
    @Positive(message = "Unit price must be positive")
    private Double unitPrice;

    public OrderItemRequest() {
    }

    public OrderItemRequest(Long sareeId, String sareeName, String sku, Integer quantity, Double unitPrice) {
        this.sareeId = sareeId;
        this.sareeName = sareeName;
        this.sku = sku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long sareeId;
        private String sareeName;
        private String sku;
        private Integer quantity;
        private Double unitPrice;

        public Builder sareeId(Long sareeId) { this.sareeId = sareeId; return this; }
        public Builder sareeName(String sareeName) { this.sareeName = sareeName; return this; }
        public Builder sku(String sku) { this.sku = sku; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder unitPrice(Double unitPrice) { this.unitPrice = unitPrice; return this; }

        public OrderItemRequest build() {
            return new OrderItemRequest(sareeId, sareeName, sku, quantity, unitPrice);
        }
    }

    public Long getSareeId() { return sareeId; }
    public void setSareeId(Long sareeId) { this.sareeId = sareeId; }

    public String getSareeName() { return sareeName; }
    public void setSareeName(String sareeName) { this.sareeName = sareeName; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }
}
