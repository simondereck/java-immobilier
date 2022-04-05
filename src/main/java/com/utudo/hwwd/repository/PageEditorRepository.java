package com.utudo.hwwd.repository;

import com.utudo.hwwd.models.PageEditor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PageEditorRepository extends JpaRepository<PageEditor,Long> {


    @Override
    Optional<PageEditor> findById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Query(nativeQuery = true, value =
            "select * from hw_pages  WHERE `ptype` = :ptype limit 1")
    PageEditor findByTypeParamsNative(@Param("ptype") int ptype);



    @Query(nativeQuery = true, value =
            "select * from hw_pages  WHERE `ptype` = :ptype and `i_status` = :status  limit 1")
    PageEditor findByTypeAndStatusParamsNative(@Param("ptype") int ptype,@Param("status") int status);
}
