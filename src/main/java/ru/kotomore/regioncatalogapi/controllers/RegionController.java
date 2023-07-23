package ru.kotomore.regioncatalogapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.ErrorMessage;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;
import ru.kotomore.regioncatalogapi.services.RegionServiceUseCase;
import ru.kotomore.regioncatalogapi.utils.RegionRequestValidator;

@RestController
@RequestMapping("/api/regions")
@RequiredArgsConstructor
public class RegionController {

    private final RegionServiceUseCase regionService;

    @GetMapping
    @Operation(summary = "Получение списка регионов")
    public ResponseEntity<?> getRegions(
            @Parameter(description = "Поиск по совпадению сокращенного названия")
            @RequestParam(required = false) String abbreviation,
            @Parameter(description = "Поиск по совпадению названия региона")
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
    @Operation(summary = "Получение региона по ID")
    public ResponseEntity<?> getRegionById(@PathVariable Long id) {
        return ResponseEntity.ok().body(regionService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Добавить новый регион")
    public ResponseEntity<?> createRegion(@RequestBody CreateRegionRequest createRegionRequest) {
        RegionRequestValidator.validateCreateRegionRequest(createRegionRequest);
        return ResponseEntity.ok().body(regionService.save(createRegionRequest));
    }

    @PutMapping
    @Operation(summary = "Обновить существующий регион")
    public ResponseEntity<?> updateRegion(@RequestBody UpdateRegionRequest updateRegionRequest) {
        RegionRequestValidator.validateUpdateRegionRequest(updateRegionRequest);
        return ResponseEntity.ok().body(regionService.update(updateRegionRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить регион по ID")
    public ResponseEntity<?> deleteRegionById(@PathVariable Long id) {
        regionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    @Operation(summary = "Удалить все регионы")
    public ResponseEntity<?> deleteAllRegions() {
        regionService.deleteAll();
        return ResponseEntity.ok().build();
    }

}
