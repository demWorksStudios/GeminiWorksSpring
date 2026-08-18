package com.saree.catalog.config;

import com.saree.catalog.model.Saree;
import com.saree.catalog.repository.SareeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initCatalogData(SareeRepository sareeRepository) {
        return args -> {
            if (sareeRepository.count() == 0) {
                List<Saree> sampleSarees = List.of(
                        Saree.builder()
                                .name("Royal Crimson Kanjeevaram Pure Silk Saree")
                                .description("Handwoven pure mulberry silk with rich golden zari korvai temple border and intricate floral pallu.")
                                .fabric("Kanjeevaram Silk")
                                .color("Crimson Red")
                                .pattern("Temple Border Zari")
                                .occasion("Bridal")
                                .blousePieceIncluded(true)
                                .price(18999.00)
                                .discountPercentage(10.0)
                                .sku("SAR-KAN-RED-001")
                                .imageUrl("https://images.unsplash.com/photo-1610030469983-98e550d6193c")
                                .rating(4.9)
                                .build(),
                        Saree.builder()
                                .name("Emerald Green Banarasi Katan Silk Saree")
                                .description("Exquisite Varanasi handloom featuring ornate floral kadwa jaal motifs in antique gold zari.")
                                .fabric("Banarasi Silk")
                                .color("Emerald Green")
                                .pattern("Kadwa Floral Jaal")
                                .occasion("Wedding")
                                .blousePieceIncluded(true)
                                .price(14500.00)
                                .discountPercentage(15.0)
                                .sku("SAR-BAN-GRN-002")
                                .imageUrl("https://images.unsplash.com/photo-1617627143750-d86bc21e42bb")
                                .rating(4.8)
                                .build(),
                        Saree.builder()
                                .name("Midnight Blue Chanderi Zari Butta Saree")
                                .description("Lightweight sheer Chanderi silk-cotton blend with fine coin buttas and an ornate tissue border.")
                                .fabric("Chanderi")
                                .color("Midnight Blue")
                                .pattern("Coin Buttas & Tissue Border")
                                .occasion("Festive")
                                .blousePieceIncluded(true)
                                .price(5999.00)
                                .discountPercentage(5.0)
                                .sku("SAR-CHA-BLU-003")
                                .imageUrl("https://images.unsplash.com/photo-1583391733956-3750e0ff4e8b")
                                .rating(4.7)
                                .build(),
                        Saree.builder()
                                .name("Mustard Yellow Tussar Silk Hand Block Printed Saree")
                                .description("Wild organic Tussar silk enriched with tribal Kantha stitch embroidery and hand block prints.")
                                .fabric("Tussar Silk")
                                .color("Mustard Yellow")
                                .pattern("Kantha Hand Embroidery")
                                .occasion("Festive")
                                .blousePieceIncluded(true)
                                .price(7499.00)
                                .discountPercentage(12.0)
                                .sku("SAR-TUS-YEL-004")
                                .imageUrl("https://images.unsplash.com/photo-1610030469668-93530c17b58f")
                                .rating(4.6)
                                .build(),
                        Saree.builder()
                                .name("Pastel Pink Floral Organza Silk Saree")
                                .description("Modern, airy organza silk with digital floral print and scalloped sequin-embroidered borders.")
                                .fabric("Organza")
                                .color("Pastel Pink")
                                .pattern("Digital Floral & Scallop Border")
                                .occasion("Party")
                                .blousePieceIncluded(true)
                                .price(4999.00)
                                .discountPercentage(8.0)
                                .sku("SAR-ORG-PNK-005")
                                .imageUrl("https://images.unsplash.com/photo-1596461404969-9ae70f2830c1")
                                .rating(4.8)
                                .build(),
                        Saree.builder()
                                .name("Maroon & Gold Pure Bandhani Silk Saree")
                                .description("Traditional Gujarati tie-and-dye Gharchola pattern on pure gajji silk with heavy zari pallu.")
                                .fabric("Bandhani")
                                .color("Maroon")
                                .pattern("Gharchola Check Grid")
                                .occasion("Wedding")
                                .blousePieceIncluded(true)
                                .price(11999.00)
                                .discountPercentage(10.0)
                                .sku("SAR-BAN-MAR-006")
                                .imageUrl("https://images.unsplash.com/photo-1609357605129-26f69add5d6e")
                                .rating(4.9)
                                .build()
                );
                sareeRepository.saveAll(sampleSarees);
            }
        };
    }
}
