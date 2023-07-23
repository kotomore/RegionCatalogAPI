package ru.kotomore.regioncatalogapi.utils;

import ru.kotomore.regioncatalogapi.dto.RegionRequest;
import ru.kotomore.regioncatalogapi.exceptions.BadRequestRegionException;

public class RegionRequestValidator {

    public static void validateRegionRequest(RegionRequest regionRequest) {
        if (regionRequest.abbreviation() == null || regionRequest.name() == null) {
            throw new BadRequestRegionException("Не все поля запроса заполнены");
        }
    }

}
