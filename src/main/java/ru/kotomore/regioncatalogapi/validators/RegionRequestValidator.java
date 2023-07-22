package ru.kotomore.regioncatalogapi.validators;

import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;
import ru.kotomore.regioncatalogapi.exceptions.BadRequestRegionException;

public class RegionRequestValidator {

    public static void validateUpdateRegionRequest(UpdateRegionRequest updateRegionRequest) {
        if (updateRegionRequest.id() == null || updateRegionRequest.abbreviation() == null ||
                updateRegionRequest.name() == null) {
            throw new BadRequestRegionException("Не все поля запроса заполнены");
        }
    }

    public static void validateCreateRegionRequest(CreateRegionRequest createRegionRequest) {
        if (createRegionRequest.abbreviation() == null || createRegionRequest.name() == null) {
            throw new BadRequestRegionException("Не все поля запроса заполнены");
        }
    }

}
