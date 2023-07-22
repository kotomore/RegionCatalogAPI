package ru.kotomore.regioncatalogapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;
import ru.kotomore.regioncatalogapi.entities.Region;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotDeletedException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotFoundException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotSavedException;
import ru.kotomore.regioncatalogapi.repositories.RegionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService implements RegionServiceUseCase {

    private final RegionRepository regionRepository;

    @Override
    public RegionResponse findById(Long id) {
        return regionRepository.findById(id)
                .map(this::mapToRegionResponse)
                .orElseThrow(() -> new RegionNotFoundException(id));
    }

    @Override
    public List<RegionResponse> findAll() {
        return regionRepository.findAll().stream()
                .map(this::mapToRegionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RegionResponse save(CreateRegionRequest regionRequest) {
        Region regionToSave = new Region(regionRequest.name(), regionRequest.abbreviation());
        if (!regionRepository.save(regionToSave)) {
            return mapToRegionResponse(regionToSave);
        } else {
            throw new RegionNotSavedException();
        }
    }

    @Override
    public RegionResponse update(UpdateRegionRequest regionRequest) {
        Region regionToUpdate = new Region(regionRequest.id(), regionRequest.name(), regionRequest.abbreviation());
        if (regionRepository.update(regionToUpdate)) {
            return mapToRegionResponse(regionToUpdate);
        } else {
            throw new RegionNotSavedException(regionRequest.id());
        }
    }

    @Override
    public void deleteById(Long id) {
        if (!regionRepository.deleteById(id)) {
            throw new RegionNotDeletedException(id);
        }
    }

    @Override
    public void deleteAll() {
        if (!regionRepository.deleteAll()) {
            throw new RegionNotDeletedException();
        }
    }

    @Override
    public List<RegionResponse> findByAbbreviation(String abbreviation) {
        return regionRepository.findByAbbreviation(abbreviation)
                .stream()
                .map(this::mapToRegionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RegionResponse> findByName(String name) {
        return regionRepository.findByName(name)
                .stream()
                .map(this::mapToRegionResponse)
                .collect(Collectors.toList());
    }


    private RegionResponse mapToRegionResponse(Region region) {
        return new RegionResponse(region.getId(), region.getName(), region.getAbbreviation());
    }

}
