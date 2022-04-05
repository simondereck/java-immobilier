package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.neuf.NeufModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NeufRepository extends JpaRepository<NeufModel,Long> {

    @Override
    Optional<NeufModel> findById(Long aLong);

    @Override
    void deleteById(Long aLong);


}
