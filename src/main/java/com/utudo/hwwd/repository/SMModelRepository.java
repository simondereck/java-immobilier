package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.SMModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SMModelRepository extends JpaRepository<SMModel,Long> {


    @Override
    SMModel getOne(Long aLong);

    @Override
    Optional<SMModel> findById(Long aLong);

    @Override
    List<SMModel> findAllById(Iterable<Long> iterable);

    @Override
    void deleteById(Long aLong);




}
