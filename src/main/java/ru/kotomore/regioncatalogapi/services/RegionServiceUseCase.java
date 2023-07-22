package ru.kotomore.regioncatalogapi.services;

import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;

import java.util.List;

public interface RegionServiceUseCase {
    RegionResponse findById(Long id);
    List<RegionResponse> findAll();
    RegionResponse save(CreateRegionRequest regionRequest);
    RegionResponse update(UpdateRegionRequest regionRequest);
    void deleteById(Long id);
    void deleteAll();
    List<RegionResponse> findByAbbreviation(String abbreviation);
    List<RegionResponse> findByName(String name);

}
