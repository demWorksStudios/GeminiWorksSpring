package com.saree.catalog.controller;

import com.saree.catalog.model.Saree;
import com.saree.catalog.service.SareeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sarees")
public class SareeController {

    private final SareeService sareeService;

    public SareeController(SareeService sareeService) {
        this.sareeService = sareeService;
    }

    @PostMapping
    public ResponseEntity<Saree> createSaree(@Valid @RequestBody Saree saree) {
        Saree created = sareeService.createSaree(saree);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<Saree>> saveAllSarees(@Valid @RequestBody List<Saree> sarees) {
        List<Saree> createdList = sareeService.saveAllSarees(sarees);
        return new ResponseEntity<>(createdList, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Saree>> getAllSarees(
            @RequestParam(name = "fabric", required = false) String fabric,
            @RequestParam(name = "occasion", required = false) String occasion,
            @RequestParam(name = "color", required = false) String color,
            @RequestParam(name = "minPrice", required = false) Double minPrice,
            @RequestParam(name = "maxPrice", required = false) Double maxPrice,
            @RequestParam(name = "search", required = false) String search) {

        if (search != null && !search.isBlank()) {
            return ResponseEntity.ok(sareeService.searchSarees(search));
        }
        if (fabric != null && !fabric.isBlank()) {
            return ResponseEntity.ok(sareeService.getSareesByFabric(fabric));
        }
        if (occasion != null && !occasion.isBlank()) {
            return ResponseEntity.ok(sareeService.getSareesByOccasion(occasion));
        }
        if (color != null && !color.isBlank()) {
            return ResponseEntity.ok(sareeService.getSareesByColor(color));
        }
        if (minPrice != null && maxPrice != null) {
            return ResponseEntity.ok(sareeService.getSareesByPriceRange(minPrice, maxPrice));
        }

        return ResponseEntity.ok(sareeService.getAllSarees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Saree> getSareeById(@PathVariable Long id) {
        return ResponseEntity.ok(sareeService.getSareeById(id));
    }

    @GetMapping("/sku/{sku}")
    public ResponseEntity<Saree> getSareeBySku(@PathVariable String sku) {
        return ResponseEntity.ok(sareeService.getSareeBySku(sku));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Saree> updateSaree(@PathVariable Long id, @Valid @RequestBody Saree saree) {
        return ResponseEntity.ok(sareeService.updateSaree(id, saree));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSaree(@PathVariable Long id) {
        sareeService.deleteSaree(id);
        return ResponseEntity.noContent().build();
    }
}
