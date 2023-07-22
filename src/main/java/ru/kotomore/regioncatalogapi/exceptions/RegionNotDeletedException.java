package ru.kotomore.regioncatalogapi.exceptions;

public class RegionNotDeletedException extends RuntimeException {

    public RegionNotDeletedException() {
        super("Ошибка удаления регионов");
    }

    public RegionNotDeletedException(Long id) {
        super("Ошибка удаления региона с ID - %s. Возможно, регион с указанным ID не существует".formatted(id));
    }

}
