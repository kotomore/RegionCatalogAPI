package ru.kotomore.regioncatalogapi.utils;

import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;
import ru.kotomore.regioncatalogapi.entities.Region;

public class RegionMapper {
    public static RegionResponse mapToRegionResponse(Region region) {
        if (region == null) {
            return null;
        }
        return new RegionResponse(region.getId(), region.getName(), region.getAbbreviation());
    }

    public static Region mapToRegion(CreateRegionRequest createRegionRequest) {
        if (createRegionRequest == null) {
            return null;
        }
        return new Region(createRegionRequest.name(), createRegionRequest.abbreviation());
    }

    public static Region mapToRegion(UpdateRegionRequest updateRegionRequest) {
        if (updateRegionRequest == null) {
            return null;
        }
        return new Region(updateRegionRequest.id(), updateRegionRequest.name(), updateRegionRequest.abbreviation());
    }
}
