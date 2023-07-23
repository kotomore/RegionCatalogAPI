package ru.kotomore.regioncatalogapi.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.kotomore.regioncatalogapi.dto.RegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.entities.Region;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotDeletedException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotFoundException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotSavedException;
import ru.kotomore.regioncatalogapi.repositories.RegionRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegionServiceTest {

    @Mock
    RegionRepository regionRepository;

    @InjectMocks
    RegionService regionService;


    @Test
    public void testFindById_ExistingId_ReturnsRegionResponse() {

        Long id = 1L;
        Region region = getRegion(id);

        when(regionRepository.findById(id)).thenReturn(Optional.of(region));

        RegionResponse result = regionService.findById(id);
        assertNotNull(result);
        assertEquals(region.getId(), result.id());
    }

    @Test
    public void testFindById_NonExistingId_ThrowsRegionNotFoundException() {

        Long nonExistingId = 100L;
        when(regionRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThrows(RegionNotFoundException.class, () -> regionService.findById(nonExistingId));
    }

    @Test
    public void testFindAll_ReturnsListOfRegionResponse() {

        List<Region> regions = Arrays.asList(getRegion(1L), getRegion(2L));
        when(regionRepository.findAll()).thenReturn(regions);

        List<RegionResponse> result = regionService.findAll();
        assertNotNull(result);
        assertEquals(regions.size(), result.size());
    }

    @Test
    public void testUpdate_ValidRegionRequest_ReturnsUpdatedRegionResponse() {
        Long id = 1L;
        RegionRequest regionRequest = getRegionRequest();
        Region regionToUpdate = getRegion(id);
        when(regionRepository.update(regionToUpdate)).thenReturn(true);

        RegionResponse result = regionService.update(id, regionRequest);

        assertNotNull(result);
        assertEquals(regionRequest.name(), result.name());
        assertEquals(regionRequest.abbreviation(), result.abbreviation());

    }

    @Test
    public void testUpdate_InvalidRegionRequest_ThrowsRegionNotSavedException() {
        RegionRequest invalidRegionRequest = getRegionRequest();
        when(regionRepository.update(any(Region.class))).thenReturn(false);

        assertThrows(RegionNotSavedException.class, () -> regionService.update(1L, invalidRegionRequest));
    }

    @Test
    public void testDeleteById_ExistingId_DeletesRegion() {
        Long idToDelete = 1L;
        when(regionRepository.deleteById(idToDelete)).thenReturn(true);

        assertDoesNotThrow(() -> regionService.deleteById(idToDelete));
    }

    @Test
    public void testDeleteById_NonExistingId_ThrowsRegionNotDeletedException() {
        Long nonExistingId = 100L;
        when(regionRepository.deleteById(nonExistingId)).thenReturn(false);

        assertThrows(RegionNotDeletedException.class, () -> regionService.deleteById(nonExistingId));
    }

    @Test
    public void testDeleteAll_DeletesAllRegions() {
        when(regionRepository.deleteAll()).thenReturn(true);

        assertDoesNotThrow(regionService::deleteAll);
    }

    @Test
    public void testDeleteAll_FailedToDelete_ThrowsRegionNotDeletedException() {
        when(regionRepository.deleteAll()).thenReturn(false);

        assertThrows(RegionNotDeletedException.class, regionService::deleteAll);
    }

    @Test
    public void testFindByAbbreviation_ExistingAbbreviation_ReturnsListOfRegionResponse() {
        String abbreviation = "AB";
        List<Region> regions = Arrays.asList(getRegion(1L), getRegion(2L));
        when(regionRepository.findByAbbreviation(abbreviation)).thenReturn(regions);

        List<RegionResponse> result = regionService.findByAbbreviation(abbreviation);

        assertNotNull(result);
        assertEquals(regions.size(), result.size());
    }

    @Test
    public void testFindByAbbreviation_NonExistingAbbreviation_ReturnsEmptyList() {
        String nonExistingAbbreviation = "XYZ";
        when(regionRepository.findByAbbreviation(nonExistingAbbreviation)).thenReturn(Collections.emptyList());

        List<RegionResponse> result = regionService.findByAbbreviation(nonExistingAbbreviation);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindByName_ExistingName_ReturnsListOfRegionResponse() {
        String name = "Region Name";
        List<Region> regions = Arrays.asList(getRegion(1L), getRegion(2L));
        when(regionRepository.findByName(name)).thenReturn(regions);

        List<RegionResponse> result = regionService.findByName(name);

        assertNotNull(result);
        assertEquals(regions.size(), result.size());
    }

    @Test
    public void testFindByName_NonExistingName_ReturnsEmptyList() {
        // Arrange
        String nonExistingName = "Non-Existent Region";
        when(regionRepository.findByName(nonExistingName)).thenReturn(Collections.emptyList());

        List<RegionResponse> result = regionService.findByName(nonExistingName);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }


    private static Region getRegion(Long id) {
        return new Region(id, "Some name", "Some abbreviation");
    }

    private static RegionRequest getRegionRequest() {
        return new RegionRequest("Some name", "Some abbreviation");
    }
}
