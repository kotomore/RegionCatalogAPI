package ru.kotomore.regioncatalogapi.utils;

import ru.kotomore.regioncatalogapi.dto.RegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.entities.Region;

public class RegionMapper {
    public static RegionResponse mapToRegionResponse(Region region) {
        if (region == null) {
            return null;
        }
        return new RegionResponse(region.getId(), region.getName(), region.getAbbreviation());
    }

    public static Region mapToRegion(RegionRequest regionRequest) {
        if (regionRequest == null) {
            return null;
        }
        return new Region(regionRequest.name(), regionRequest.abbreviation());
    }
}
