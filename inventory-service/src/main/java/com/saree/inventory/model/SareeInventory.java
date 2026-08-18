package com.saree.inventory.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "saree_inventories")
public class SareeInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "SKU is mandatory")
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @NotNull(message = "Available stock is mandatory")
    @Column(name = "available_stock", nullable = false)
    private Integer availableStock;

    @Column(name = "reserved_stock")
    private Integer reservedStock;

    public SareeInventory() {
    }

    public SareeInventory(Long id, String sku, Integer availableStock, Integer reservedStock) {
        this.id = id;
        this.sku = sku;
        this.availableStock = availableStock;
        this.reservedStock = reservedStock;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String sku;
        private Integer availableStock;
        private Integer reservedStock;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder sku(String sku) { this.sku = sku; return this; }
        public Builder availableStock(Integer availableStock) { this.availableStock = availableStock; return this; }
        public Builder reservedStock(Integer reservedStock) { this.reservedStock = reservedStock; return this; }

        public SareeInventory build() {
            return new SareeInventory(id, sku, availableStock, reservedStock);
        }
    }

    public boolean isInStock() {
        return availableStock != null && availableStock > 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public Integer getAvailableStock() { return availableStock; }
    public void setAvailableStock(Integer availableStock) { this.availableStock = availableStock; }

    public Integer getReservedStock() { return reservedStock; }
    public void setReservedStock(Integer reservedStock) { this.reservedStock = reservedStock; }
}
