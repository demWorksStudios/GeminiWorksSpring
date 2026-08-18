package com.saree.catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "sarees")
public class Saree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Saree name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @NotBlank(message = "Fabric is mandatory")
    @Column(name = "fabric", nullable = false)
    private String fabric;

    @NotBlank(message = "Color is mandatory")
    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "pattern")
    private String pattern;

    @Column(name = "occasion")
    private String occasion;

    @Column(name = "blouse_piece_included")
    private Boolean blousePieceIncluded;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "discount_percentage")
    private Double discountPercentage;

    @NotBlank(message = "SKU is mandatory")
    @Column(name = "sku", nullable = false, unique = true)
    private String sku;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "rating")
    private Double rating;

    public Saree() {
    }

    public Saree(Long id, String name, String description, String fabric, String color, String pattern,
                 String occasion, Boolean blousePieceIncluded, Double price, Double discountPercentage,
                 String sku, String imageUrl, Double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.fabric = fabric;
        this.color = color;
        this.pattern = pattern;
        this.occasion = occasion;
        this.blousePieceIncluded = blousePieceIncluded;
        this.price = price;
        this.discountPercentage = discountPercentage;
        this.sku = sku;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String name;
        private String description;
        private String fabric;
        private String color;
        private String pattern;
        private String occasion;
        private Boolean blousePieceIncluded;
        private Double price;
        private Double discountPercentage;
        private String sku;
        private String imageUrl;
        private Double rating;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder description(String description) { this.description = description; return this; }
        public Builder fabric(String fabric) { this.fabric = fabric; return this; }
        public Builder color(String color) { this.color = color; return this; }
        public Builder pattern(String pattern) { this.pattern = pattern; return this; }
        public Builder occasion(String occasion) { this.occasion = occasion; return this; }
        public Builder blousePieceIncluded(Boolean blousePieceIncluded) { this.blousePieceIncluded = blousePieceIncluded; return this; }
        public Builder price(Double price) { this.price = price; return this; }
        public Builder discountPercentage(Double discountPercentage) { this.discountPercentage = discountPercentage; return this; }
        public Builder sku(String sku) { this.sku = sku; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder rating(Double rating) { this.rating = rating; return this; }

        public Saree build() {
            return new Saree(id, name, description, fabric, color, pattern, occasion,
                    blousePieceIncluded, price, discountPercentage, sku, imageUrl, rating);
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getFabric() { return fabric; }
    public void setFabric(String fabric) { this.fabric = fabric; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getPattern() { return pattern; }
    public void setPattern(String pattern) { this.pattern = pattern; }

    public String getOccasion() { return occasion; }
    public void setOccasion(String occasion) { this.occasion = occasion; }

    public Boolean getBlousePieceIncluded() { return blousePieceIncluded; }
    public void setBlousePieceIncluded(Boolean blousePieceIncluded) { this.blousePieceIncluded = blousePieceIncluded; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getDiscountPercentage() { return discountPercentage; }
    public void setDiscountPercentage(Double discountPercentage) { this.discountPercentage = discountPercentage; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Double getRating() { return rating; }
    public void setRating(Double rating) { this.rating = rating; }
}
