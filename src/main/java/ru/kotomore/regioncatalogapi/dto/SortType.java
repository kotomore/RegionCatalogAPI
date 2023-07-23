package ru.kotomore.regioncatalogapi.dto;

public enum SortType {
    ID("id"),
    NAME("name"),
    ABBREVIATION("abbreviation");

    private final String text;

    SortType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
