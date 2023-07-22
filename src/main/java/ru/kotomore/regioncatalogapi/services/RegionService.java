package ru.kotomore.regioncatalogapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;
import ru.kotomore.regioncatalogapi.entities.Region;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotDeletedException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotFoundException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotSavedException;
import ru.kotomore.regioncatalogapi.repositories.RegionRepository;
import ru.kotomore.regioncatalogapi.utils.RegionMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService implements RegionServiceUseCase {

    private final RegionRepository regionRepository;

    @Override
    @Cacheable(value = "regions", key = "#id")
    public RegionResponse findById(Long id) {
        return regionRepository.findById(id)
                .map(RegionMapper::mapToRegionResponse)
                .orElseThrow(() -> new RegionNotFoundException(id));
    }

    @Override
    @Cacheable("regions")
    public List<RegionResponse> findAll() {
        return regionRepository.findAll().stream()
                .map(RegionMapper::mapToRegionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {"regions", "regionsByAbbreviation", "regionsByName"}, allEntries = true)
    public RegionResponse save(CreateRegionRequest regionRequest) {
        Region regionToSave = RegionMapper.mapToRegion(regionRequest);
        if (!regionRepository.save(regionToSave)) {
            return RegionMapper.mapToRegionResponse(regionToSave);
        } else {
            throw new RegionNotSavedException();
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "regions", key = "#regionRequest.id()"),
            @CacheEvict(value = "regionsByAbbreviation", allEntries = true),
            @CacheEvict(value = "regionsByName", allEntries = true)

    })
    public RegionResponse update(UpdateRegionRequest regionRequest) {
        Region regionToUpdate = RegionMapper.mapToRegion(regionRequest);
        if (regionRepository.update(regionToUpdate)) {
            return RegionMapper.mapToRegionResponse(regionToUpdate);
        } else {
            throw new RegionNotSavedException(regionRequest.id());
        }
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "regions", key = "#id"),
            @CacheEvict(value = "regionsByAbbreviation", allEntries = true),
            @CacheEvict(value = "regionsByName", allEntries = true)

    })
    public void deleteById(Long id) {
        if (!regionRepository.deleteById(id)) {
            throw new RegionNotDeletedException(id);
        }
    }

    @Override
    @CacheEvict(value = {"regions", "regionsByAbbreviation", "regionsByName"}, allEntries = true)
    public void deleteAll() {
        if (!regionRepository.deleteAll()) {
            throw new RegionNotDeletedException();
        }
    }

    @Override
    @Cacheable(value = "regionsByAbbreviation", key = "#abbreviation")
    public List<RegionResponse> findByAbbreviation(String abbreviation) {
        return regionRepository.findByAbbreviation(abbreviation)
                .stream()
                .map(RegionMapper::mapToRegionResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "regionsByName", key = "#name")
    public List<RegionResponse> findByName(String name) {
        return regionRepository.findByName(name)
                .stream()
                .map(RegionMapper::mapToRegionResponse)
                .collect(Collectors.toList());
    }

}
