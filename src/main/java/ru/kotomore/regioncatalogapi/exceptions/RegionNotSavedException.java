package ru.kotomore.regioncatalogapi.exceptions;

public class RegionNotSavedException extends RuntimeException {

    public RegionNotSavedException() {
        super("Ошибка сохранения региона");
    }

    public RegionNotSavedException(Long id) {
        super("Ошибка обновления региона с ID - %s. Возможно, регион с указанным ID не существует".formatted(id));
    }

}
