package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.Universite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UniversiteRepository extends JpaRepository<Universite,Long> {

    Universite findById(long id);

    @Override
    void deleteById(Long aLong);



}
