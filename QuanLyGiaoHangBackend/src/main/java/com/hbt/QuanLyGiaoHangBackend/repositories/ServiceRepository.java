package com.hbt.QuanLyGiaoHangBackend.repositories;


import com.hbt.QuanLyGiaoHangBackend.pojo.Service;
import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<com.hbt.QuanLyGiaoHangBackend.pojo.Service, Integer> {
    Optional<com.hbt.QuanLyGiaoHangBackend.pojo.Service> findByName(String name);
    List<Service> findAll(Specification<Service> specService);
    Page<Service> findAll(Specification<Service> specService, Pageable pageable);
}