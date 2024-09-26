package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper,Long>, JpaSpecificationExecutor<Shipper> {
    boolean existsByCmnd(String cmnd);

    Page<Shipper> findAll(Specification<Shipper> specShipper, Pageable pageable);
}
