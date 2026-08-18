package com.saree.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class OrderRequest {

    @NotBlank(message = "Customer name is mandatory")
    private String customerName;

    @NotBlank(message = "Customer email is mandatory")
    @Email(message = "Invalid email format")
    private String customerEmail;

    private String customerPhone;

    @NotBlank(message = "Shipping address is mandatory")
    private String shippingAddress;

    private String paymentMode;

    @NotEmpty(message = "Order must have at least one saree item")
    @Valid
    private List<OrderItemRequest> items;

    public OrderRequest() {
    }

    public OrderRequest(String customerName, String customerEmail, String customerPhone,
                        String shippingAddress, String paymentMode, List<OrderItemRequest> items) {
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.shippingAddress = shippingAddress;
        this.paymentMode = paymentMode;
        this.items = items;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String customerName;
        private String customerEmail;
        private String customerPhone;
        private String shippingAddress;
        private String paymentMode;
        private List<OrderItemRequest> items;

        public Builder customerName(String customerName) { this.customerName = customerName; return this; }
        public Builder customerEmail(String customerEmail) { this.customerEmail = customerEmail; return this; }
        public Builder customerPhone(String customerPhone) { this.customerPhone = customerPhone; return this; }
        public Builder shippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; return this; }
        public Builder paymentMode(String paymentMode) { this.paymentMode = paymentMode; return this; }
        public Builder items(List<OrderItemRequest> items) { this.items = items; return this; }

        public OrderRequest build() {
            return new OrderRequest(customerName, customerEmail, customerPhone, shippingAddress, paymentMode, items);
        }
    }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getPaymentMode() { return paymentMode; }
    public void setPaymentMode(String paymentMode) { this.paymentMode = paymentMode; }

    public List<OrderItemRequest> getItems() { return items; }
    public void setItems(List<OrderItemRequest> items) { this.items = items; }
}
