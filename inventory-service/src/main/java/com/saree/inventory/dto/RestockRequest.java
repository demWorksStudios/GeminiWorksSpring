package com.saree.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RestockRequest {

    @NotBlank(message = "SKU is required")
    private String sku;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer additionalQuantity;

    public RestockRequest() {
    }

    public RestockRequest(String sku, Integer additionalQuantity) {
        this.sku = sku;
        this.additionalQuantity = additionalQuantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sku;
        private Integer additionalQuantity;

        public Builder sku(String sku) { this.sku = sku; return this; }
        public Builder additionalQuantity(Integer additionalQuantity) { this.additionalQuantity = additionalQuantity; return this; }

        public RestockRequest build() {
            return new RestockRequest(sku, additionalQuantity);
        }
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getAdditionalQuantity() { return additionalQuantity; }
    public void setAdditionalQuantity(Integer additionalQuantity) { this.additionalQuantity = additionalQuantity; }
}
