package com.hbt.QuanLyGiaoHangBackend.repositories.impl;

import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.repositories.DauGiaRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DauGiaRepositoryImpl implements DauGiaRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Object[]> getPriceByPeriodAndYear(int year, String inputPeriod) {
        String period = inputPeriod.toUpperCase();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        Root<DauGia> dauGiaRoot = query.from(DauGia.class);
        Join<DauGia, Product> productJoin = dauGiaRoot.join("product");

        // Chọn tháng từ `paymentDate` và tổng giá từ `price`
        query.multiselect(
                cb.function(period, Integer.class, productJoin.get("paymentDate")),
                cb.sum(dauGiaRoot.get("price"))
        );

        // Điều kiện năm
        query.where(cb.equal(cb.function("YEAR", Integer.class, productJoin.get("paymentDate")), year));

        // Nhóm theo tháng
        query.groupBy(cb.function(period, Integer.class, productJoin.get("paymentDate")));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Object[]> countSuccessProductByPeriodAndYear(int year, String inputPeriod) {
        String period = inputPeriod.toUpperCase();

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        Root<DauGia> dauGiaRoot = query.from(DauGia.class);
        Join<DauGia, Product> productJoin = dauGiaRoot.join("product");

        // Chọn tháng từ `paymentDate` và tổng giá từ `price`
        query.multiselect(
                cb.function(period, Integer.class, productJoin.get("paymentDate")),
                cb.sum(dauGiaRoot.get("price"))
        );

        // Điều kiện năm
        query.where(cb.equal(cb.function("YEAR", Integer.class, productJoin.get("paymentDate")), year));

        // Nhóm theo tháng
        query.groupBy(cb.function(period, Integer.class, productJoin.get("paymentDate")));

        return entityManager.createQuery(query).getResultList();
    }
}
