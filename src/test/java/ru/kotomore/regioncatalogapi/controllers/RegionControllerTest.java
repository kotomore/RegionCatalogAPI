package ru.kotomore.regioncatalogapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.kotomore.regioncatalogapi.dto.RegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.services.RegionService;
import ru.kotomore.regioncatalogapi.services.RegionServiceUseCase;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RegionControllerTest {

    @Mock
    private RegionServiceUseCase regionService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
        regionService = Mockito.mock(RegionService.class);
    }

    @Test
    public void testGetAllRegions_ShouldReturnAllRegionsList() throws Exception {
        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();

        when(regionService.findAll(null)).thenReturn(List.of(getRegionResponse(1L), getRegionResponse(2L)));

        mockMvc.perform(get("/api/regions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetRegionsByName_ShouldReturnRegionsList() throws Exception {
        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();
        String name = "abc";

        when(regionService.findByName(name, null)).thenReturn(List.of(getRegionResponse(1L), getRegionResponse(2L)));

        mockMvc.perform(get("/api/regions")
                        .param("name", name))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetRegionsByAbbreviation_ShouldReturnRegionsList() throws Exception {
        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();
        String abbreviation = "abc";

        when(regionService.findByAbbreviation(abbreviation, null)).thenReturn(List.of(getRegionResponse(1L), getRegionResponse(2L)));

        mockMvc.perform(get("/api/regions")
                        .param("abbreviation", abbreviation))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void testGetRegionsByAbbreviationAndName_ShouldThrowsException() throws Exception {
        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();

        mockMvc.perform(get("/api/regions")
                        .param("abbreviation", "abbreviation")
                .param("name", "name"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetRegionsById_ShouldReturnOk() throws Exception {
        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();

        RegionResponse regionResponse = getRegionResponse(1L);

        when(regionService.findById(1L)).thenReturn(regionResponse);

        mockMvc.perform(get("/api/regions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(regionResponse.name()));
    }

    @Test
    public void testCreateRegion_ShouldReturnOk() throws Exception {
        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();

        RegionResponse regionResponse = getRegionResponse(1L);
        RegionRequest regionRequest = new RegionRequest("name", "ABBR");

        when(regionService.save(any())).thenReturn(regionResponse);

        mockMvc.perform(post("/api/regions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(regionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(regionResponse.name()));
    }

    @Test
    public void testUpdateRegion_ShouldReturnOk() throws Exception {
        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();

        RegionResponse regionResponse = getRegionResponse(1L);
        RegionRequest updateRegionRequest = new RegionRequest( "name", "ABBR");

        when(regionService.update(any(), any())).thenReturn(regionResponse);

        mockMvc.perform(put("/api/regions/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRegionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(regionResponse.name()));
    }

    @Test
    public void testDeleteRegionById_ShouldReturnOk() throws Exception {
        RegionController regionController = new RegionController(regionService);
        mockMvc = MockMvcBuilders.standaloneSetup(regionController).build();

        mockMvc.perform(delete("/api/regions/1"))
                .andExpect(status().isOk());
    }

    private static RegionResponse getRegionResponse(Long id) {
        return new RegionResponse(id, "some name", "some abbreviation");
    }
}

