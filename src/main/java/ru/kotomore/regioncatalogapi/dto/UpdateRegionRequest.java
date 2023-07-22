package ru.kotomore.regioncatalogapi.dto;

public record UpdateRegionRequest(Long id, String name, String abbreviation) {
}
