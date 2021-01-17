package com.nnmpizza.delivery.repository;

import com.nnmpizza.delivery.models.FileDB;
import com.nnmpizza.delivery.models.Topping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
    @Query("SELECT f FROM FileDB f WHERE " +
            "LOWER(f.type) " +
            "LIKE :#{#type == null || #type.isEmpty()? '%' : '%'+#type+'%'}")
    Page<FileDB> findPageFiles(Pageable pageRequest, String type);
}
