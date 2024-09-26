package com.hbt.QuanLyGiaoHangBackend.repositories;


import com.hbt.QuanLyGiaoHangBackend.pojo.Category;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<com.hbt.QuanLyGiaoHangBackend.pojo.Category, Integer> {
    Optional<com.hbt.QuanLyGiaoHangBackend.pojo.Category> findByName(String name);
    Page<Category> findAll(Pageable pageable);
}