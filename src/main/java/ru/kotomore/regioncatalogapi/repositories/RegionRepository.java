package ru.kotomore.regioncatalogapi.repositories;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import ru.kotomore.regioncatalogapi.entities.Region;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface RegionRepository {

    @Select("SELECT * FROM region WHERE id = #{id}")
    Optional<Region> findById(@Param("id") Long id);

    @Select("SELECT * FROM region WHERE LOWER(name) LIKE CONCAT('%', LOWER(#{name}), '%') ORDER BY ${columnName}")
    List<Region> findByName(@Param("name") String name, @Param("columnName") String columnName);

    @Select("SELECT * FROM region WHERE LOWER(abbreviation) LIKE CONCAT('%', LOWER(#{abbreviation}), '%') ORDER BY ${columnName}")
    List<Region> findByAbbreviation(@Param("abbreviation") String abbreviation, @Param("columnName") String columnName);

    @Select("SELECT * FROM region ORDER BY ${columnName}")
    List<Region> findAll(@Param("columnName") String columnName);

    @Insert("INSERT INTO region (name, abbreviation) VALUES (#{name}, #{abbreviation})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean save(Region region);

    @Update("UPDATE region SET name=#{name}, " +
            " abbreviation=#{abbreviation} WHERE id=#{id} ")
    boolean update(Region region);

    @Delete("DELETE FROM region WHERE id = #{id}")
    boolean deleteById(Long id);

    @Delete("DELETE FROM region")
    boolean deleteAll();
}
