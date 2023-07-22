package ru.kotomore.regioncatalogapi.entities;


import lombok.Data;

@Data
public class Region {
    private Long id;
    private String name;
    private String abbreviation;

    public Region(Long id, String name, String abbreviation) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
    }

    public Region(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }
}
