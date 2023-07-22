package ru.kotomore.regioncatalogapi.exceptions;

public class RegionNotFoundException extends RuntimeException {

    public RegionNotFoundException(Long id) {
        super("Регион с ID - %s не найден".formatted(id));
    }

}
