package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.PartnerRdv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RdvRepository extends JpaRepository<PartnerRdv,Long> {


    @Override
    Optional<PartnerRdv> findById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_rdv  WHERE pid= :pid OR id= :id limit 1")
    PartnerRdv findByPidAndIdParamsNative(@Param("pid") long pid,@Param("id") long id);
}
