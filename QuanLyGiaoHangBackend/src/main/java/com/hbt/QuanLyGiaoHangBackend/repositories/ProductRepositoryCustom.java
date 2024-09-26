package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Object[]> findProductsByPeriodAndCount(int year, String inputPeriod);
    List<Object[]> findProductsByServiceAndYear(int year);
}
