package ru.kotomore.regioncatalogapi.validators;

import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;

public class RegionRequestValidator {

    public static boolean isValidUpdateRegionRequest(UpdateRegionRequest updateRegionRequest) {
        return (updateRegionRequest.id() != null && updateRegionRequest.abbreviation() != null &&
                updateRegionRequest.name() != null);
    }

    public static boolean isValidCreateRegionRequest(CreateRegionRequest createRegionRequest) {
        return (createRegionRequest.abbreviation() != null && createRegionRequest.name() != null);
    }

}
