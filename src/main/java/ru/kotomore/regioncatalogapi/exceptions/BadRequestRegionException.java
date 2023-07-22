package ru.kotomore.regioncatalogapi.exceptions;

public class BadRequestRegionException extends RuntimeException {

    public BadRequestRegionException(String text) {
        super(text);
    }

}
