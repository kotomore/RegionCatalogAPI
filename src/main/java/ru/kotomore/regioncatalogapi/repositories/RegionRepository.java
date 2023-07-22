package ru.kotomore.regioncatalogapi.repositories;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import ru.kotomore.regioncatalogapi.entities.Region;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface RegionRepository {

    @Select("select * from region where id = #{id}")
    Optional<Region> findById(@Param("id") Long id);

    @Select("SELECT * FROM region WHERE LOWER(name) LIKE CONCAT('%', LOWER(#{name}), '%')")
    List<Region> findByName(@Param("name") String name);

    @Select("SELECT * FROM region WHERE LOWER(abbreviation) LIKE CONCAT('%', LOWER(#{abbreviation}), '%')")
    List<Region> findByAbbreviation(@Param("abbreviation") String abbreviation);

    @Select("select * from region")
    List<Region> findAll();

    @Insert("insert into region (name, abbreviation) values (#{name}, #{abbreviation})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    boolean save(Region region);

    @Update("Update region set name=#{name}, " +
            " abbreviation=#{abbreviation} where id=#{id} ")
    boolean update(Region region);

    @Delete("DELETE FROM region WHERE id = #{id}")
    boolean deleteById(Long id);

    @Delete("DELETE FROM region")
    boolean deleteAll();
}
