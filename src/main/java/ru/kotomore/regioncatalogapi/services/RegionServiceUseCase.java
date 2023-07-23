package ru.kotomore.regioncatalogapi.services;

import ru.kotomore.regioncatalogapi.dto.RegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;

import java.util.List;

public interface RegionServiceUseCase {
    RegionResponse findById(Long id);
    List<RegionResponse> findAll();
    RegionResponse save(RegionRequest regionRequest);
    RegionResponse update(Long id, RegionRequest regionRequest);
    void deleteById(Long id);
    void deleteAll();
    List<RegionResponse> findByAbbreviation(String abbreviation);
    List<RegionResponse> findByName(String name);

}
