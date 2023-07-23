package ru.kotomore.regioncatalogapi.services;

import ru.kotomore.regioncatalogapi.dto.RegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.dto.SortType;

import java.util.List;

public interface RegionServiceUseCase {
    RegionResponse findById(Long id);
    List<RegionResponse> findAll(SortType sortType);
    List<RegionResponse> findByAbbreviation(String abbreviation, SortType sortType);
    List<RegionResponse> findByName(String name, SortType sortType);
    RegionResponse save(RegionRequest regionRequest);
    RegionResponse update(Long id, RegionRequest regionRequest);
    void deleteById(Long id);
    void deleteAll();

}
