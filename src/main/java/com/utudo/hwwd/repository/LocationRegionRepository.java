package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.LocationRegion;
import com.utudo.hwwd.models.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LocationRegionRepository extends JpaRepository<LocationRegion,Long> {


    @Override
    LocationRegion getOne(Long aLong);

    @Override
    Optional<LocationRegion> findById(Long aLong);

    @Override
    List<LocationRegion> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);


    @Query(nativeQuery = true,value = "select * from hw_location_region where code=:code limit 1")
    LocationRegion findLocactionByCodeParamsNative(@Param("code") long code);



}
