package com.saree.inventory.dto;

public class StockCheckResponse {
    private String sku;
    private boolean inStock;
    private int availableQuantity;

    public StockCheckResponse() {
    }

    public StockCheckResponse(String sku, boolean inStock, int availableQuantity) {
        this.sku = sku;
        this.inStock = inStock;
        this.availableQuantity = availableQuantity;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String sku;
        private boolean inStock;
        private int availableQuantity;

        public Builder sku(String sku) { this.sku = sku; return this; }
        public Builder inStock(boolean inStock) { this.inStock = inStock; return this; }
        public Builder availableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; return this; }

        public StockCheckResponse build() {
            return new StockCheckResponse(sku, inStock, availableQuantity);
        }
    }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public boolean isInStock() { return inStock; }
    public void setInStock(boolean inStock) { this.inStock = inStock; }

    public int getAvailableQuantity() { return availableQuantity; }
    public void setAvailableQuantity(int availableQuantity) { this.availableQuantity = availableQuantity; }
}
