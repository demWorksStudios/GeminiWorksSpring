package com.saree.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @Column(name = "saree_id")
    private Long sareeId;

    @Column(name = "saree_name", nullable = false)
    private String sareeName;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal;

    public OrderItem() {
    }

    public OrderItem(Long id, Order order, Long sareeId, String sareeName, String sku,
                     Integer quantity, Double unitPrice, Double subtotal) {
        this.id = id;
        this.order = order;
        this.sareeId = sareeId;
        this.sareeName = sareeName;
        this.sku = sku;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private Order order;
        private Long sareeId;
        private String sareeName;
        private String sku;
        private Integer quantity;
        private Double unitPrice;
        private Double subtotal;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder order(Order order) { this.order = order; return this; }
        public Builder sareeId(Long sareeId) { this.sareeId = sareeId; return this; }
        public Builder sareeName(String sareeName) { this.sareeName = sareeName; return this; }
        public Builder sku(String sku) { this.sku = sku; return this; }
        public Builder quantity(Integer quantity) { this.quantity = quantity; return this; }
        public Builder unitPrice(Double unitPrice) { this.unitPrice = unitPrice; return this; }
        public Builder subtotal(Double subtotal) { this.subtotal = subtotal; return this; }

        public OrderItem build() {
            return new OrderItem(id, order, sareeId, sareeName, sku, quantity, unitPrice, subtotal);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

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

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}
