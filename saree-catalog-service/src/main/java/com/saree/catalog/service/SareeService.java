package com.saree.catalog.service;

import com.saree.catalog.exception.ResourceNotFoundException;
import com.saree.catalog.model.Saree;
import com.saree.catalog.repository.SareeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SareeService {

    private final SareeRepository sareeRepository;

    public SareeService(SareeRepository sareeRepository) {
        this.sareeRepository = sareeRepository;
    }

    public Saree createSaree(Saree saree) {
        return sareeRepository.save(saree);
    }

    public List<Saree> saveAllSarees(List<Saree> sarees) {
        return sareeRepository.saveAll(sarees);
    }

    @Transactional(readOnly = true)
    public List<Saree> getAllSarees() {
        return sareeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Saree getSareeById(Long id) {
        return sareeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Saree not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Saree getSareeBySku(String sku) {
        return sareeRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Saree not found with SKU: " + sku));
    }

    @Transactional(readOnly = true)
    public List<Saree> getSareesByFabric(String fabric) {
        return sareeRepository.findByFabricIgnoreCase(fabric);
    }

    @Transactional(readOnly = true)
    public List<Saree> getSareesByOccasion(String occasion) {
        return sareeRepository.findByOccasionIgnoreCase(occasion);
    }

    @Transactional(readOnly = true)
    public List<Saree> getSareesByColor(String color) {
        return sareeRepository.findByColorIgnoreCase(color);
    }

    @Transactional(readOnly = true)
    public List<Saree> getSareesByPriceRange(Double minPrice, Double maxPrice) {
        return sareeRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Transactional(readOnly = true)
    public List<Saree> searchSarees(String query) {
        return sareeRepository.searchSarees(query);
    }

    public Saree updateSaree(Long id, Saree updatedSaree) {
        Saree existing = getSareeById(id);
        existing.setName(updatedSaree.getName());
        existing.setDescription(updatedSaree.getDescription());
        existing.setFabric(updatedSaree.getFabric());
        existing.setColor(updatedSaree.getColor());
        existing.setPattern(updatedSaree.getPattern());
        existing.setOccasion(updatedSaree.getOccasion());
        existing.setBlousePieceIncluded(updatedSaree.getBlousePieceIncluded());
        existing.setPrice(updatedSaree.getPrice());
        existing.setDiscountPercentage(updatedSaree.getDiscountPercentage());
        existing.setSku(updatedSaree.getSku());
        existing.setImageUrl(updatedSaree.getImageUrl());
        existing.setRating(updatedSaree.getRating());
        return sareeRepository.save(existing);
    }

    public void deleteSaree(Long id) {
        if (!sareeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Saree not found with id: " + id);
        }
        sareeRepository.deleteById(id);
    }
}
