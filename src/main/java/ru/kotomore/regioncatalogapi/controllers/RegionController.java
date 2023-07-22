package ru.kotomore.regioncatalogapi.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.ErrorMessage;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;
import ru.kotomore.regioncatalogapi.services.RegionServiceUseCase;
import ru.kotomore.regioncatalogapi.validators.RegionRequestValidator;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionServiceUseCase regionService;

    @GetMapping
    public ResponseEntity<?> getRegions(
            @RequestParam(required = false) String abbreviation,
            @RequestParam(required = false) String name
    ) {
        if (abbreviation != null && name != null) {
            return ResponseEntity.badRequest()
                    .body(new ErrorMessage("возможен только один фильтр: 'abbreviation' или 'name'"));
        }
        if (abbreviation != null) {
            return ResponseEntity.ok(regionService.findByAbbreviation(abbreviation));
        } else if (name != null) {
            return ResponseEntity.ok(regionService.findByName(name));
        } else {
            return ResponseEntity.ok().body(regionService.findAll());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRegionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(regionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> createRegion(@RequestBody CreateRegionRequest createRegionRequest) {
        RegionRequestValidator.validateCreateRegionRequest(createRegionRequest);
        return ResponseEntity.ok().body(regionService.save(createRegionRequest));
    }

    @PutMapping
    public ResponseEntity<?> updateRegion(@RequestBody UpdateRegionRequest updateRegionRequest) {
        RegionRequestValidator.validateUpdateRegionRequest(updateRegionRequest);
        return ResponseEntity.ok().body(regionService.update(updateRegionRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRegionById(@PathVariable Long id) {
        regionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllRegions() {
        regionService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
