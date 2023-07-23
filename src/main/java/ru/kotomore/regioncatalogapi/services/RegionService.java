package ru.kotomore.regioncatalogapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import ru.kotomore.regioncatalogapi.dto.CreateRegionRequest;
import ru.kotomore.regioncatalogapi.dto.RegionResponse;
import ru.kotomore.regioncatalogapi.dto.UpdateRegionRequest;
import ru.kotomore.regioncatalogapi.entities.Region;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotDeletedException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotFoundException;
import ru.kotomore.regioncatalogapi.exceptions.RegionNotSavedException;
import ru.kotomore.regioncatalogapi.repositories.RegionRepository;
import ru.kotomore.regioncatalogapi.utils.RegionMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegionService implements RegionServiceUseCase {

    private final RegionRepository regionRepository;

    /**
     * Найти регион по идентификатору
     *
     * @param id          Идентификатор региона
     * @return            DTO для полученного региона
     * @throws RegionNotFoundException Если такого идентификатора нет в таблице
     */
    @Override
    @Cacheable(value = "regions", key = "#id")
    public RegionResponse findById(Long id) {
        return regionRepository.findById(id)
                .map(RegionMapper::mapToRegionResponse)
                .orElseThrow(() -> new RegionNotFoundException(id));
    }

    /**
     * Получить список всех регионов
     *
     * @return   Список DTO для полученных регионов
     */
    @Override
    @Cacheable("regions")
    public List<RegionResponse> findAll() {
        return regionRepository.findAll().stream()
                .map(RegionMapper::mapToRegionResponse)
                .collect(Collectors.toList());
    }

    /**
     * Найти регион по идентификатору
     *
     * @param regionRequest   DTO для сохранения региона в таблицу
     * @return                DTO сохраненного региона с ID
     * @throws RegionNotSavedException Если не удалось сохранить регион
     */
    @Override
    @CacheEvict(value = {"regions", "regionsByAbbreviation", "regionsByName"}, allEntries = true)
    public RegionResponse save(CreateRegionRequest regionRequest) {
        Region regionToSave = RegionMapper.mapToRegion(regionRequest);
        if (!regionRepository.save(regionToSave)) {
            return RegionMapper.mapToRegionResponse(regionToSave);
        } else {
            throw new RegionNotSavedException();
        }
    }

    /**
     * Найти регион по идентификатору
     *
     * @param regionRequest    DTO для обновления региона в таблице
     * @return                 DTO для обновленного региона
     * @throws RegionNotSavedException Если не удалось обновить регион
     */
    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "regionsByAbbreviation", allEntries = true),
                    @CacheEvict(value = "regionsByName", allEntries = true)},
            put = @CachePut(value = "regions", key = "#regionRequest.id()")
    )
    public RegionResponse update(UpdateRegionRequest regionRequest) {
        Region regionToUpdate = RegionMapper.mapToRegion(regionRequest);
        if (regionRepository.update(regionToUpdate)) {
            return RegionMapper.mapToRegionResponse(regionToUpdate);
        } else {
            throw new RegionNotSavedException(regionRequest.id());
        }
    }

    /**
     * Удалить регион по идентификатору
     *
     * @param id    Идентификатор региона
     * @throws RegionNotDeletedException Если не удалось удалить регион
     */
    @Override
    @Caching(evict = {
            @CacheEvict(value = "regions", key = "#id"),
            @CacheEvict(value = "regionsByAbbreviation", allEntries = true),
            @CacheEvict(value = "regionsByName", allEntries = true)

    })
    public void deleteById(Long id) {
        if (!regionRepository.deleteById(id)) {
            throw new RegionNotDeletedException(id);
        }
    }

    /**
     * Удалить все регионы из таблицы
     * @throws RegionNotDeletedException Если не удалось удалить регион
     **/
    @Override
    @CacheEvict(value = {"regions", "regionsByAbbreviation", "regionsByName"}, allEntries = true)
    public void deleteAll() {
        if (!regionRepository.deleteAll()) {
            throw new RegionNotDeletedException();
        }
    }

    /**
     * Поиск региона по сокращенному названию
     *
     * @param abbreviation  сокращенное название
     * @return              список DTO найденных регионов
     */
    @Override
    @Cacheable(value = "regionsByAbbreviation", key = "#abbreviation")
    public List<RegionResponse> findByAbbreviation(String abbreviation) {
        return regionRepository.findByAbbreviation(abbreviation)
                .stream()
                .map(RegionMapper::mapToRegionResponse)
                .collect(Collectors.toList());
    }

    /**
     * Поиск региона по сокращенному названию
     *
     * @param name   название
     * @return       список DTO найденных регионов
     */
    @Override
    @Cacheable(value = "regionsByName", key = "#name")
    public List<RegionResponse> findByName(String name) {
        return regionRepository.findByName(name)
                .stream()
                .map(RegionMapper::mapToRegionResponse)
                .collect(Collectors.toList());
    }

}
