package com.hbt.QuanLyGiaoHangBackend.repositories.impl;

import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.repositories.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Object[]> findProductsByPeriodAndCount(int year, String inputPeriod) {
        String period = inputPeriod.toUpperCase();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> query = criteriaBuilder.createQuery(Object[].class);

        // Define the root for the Product entity
        Root<Product> root = query.from(Product.class);

        // Create the period and count selection
        query.multiselect(
                criteriaBuilder.function(period, Integer.class, root.get("paymentDate")),
                criteriaBuilder.count(root)
        );

        // Điều kiện năm
        query.where(criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("paymentDate")), year));

        // Group by month
        query.groupBy(criteriaBuilder.function(period, Integer.class, root.get("paymentDate")));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public  List<Object[]> findProductsByServiceAndYear(int year){
        // Tạo đối tượng CriteriaBuilder
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        // Tạo đối tượng CriteriaQuery để định nghĩa kiểu kết quả trả về
        CriteriaQuery<Object[]> query = cb.createQuery(Object[].class);

        // Lấy root của bảng Product
        Root<Product> product = query.from(Product.class);

        query.where(cb.equal(cb.function("YEAR", Integer.class, product.get("createdDate")), year));

        // Thực hiện group by theo tên của service và đếm số lượng product cho mỗi service
        query.multiselect(product.get("serviceId").get("name"), cb.count(product))
                .groupBy(product.get("serviceId").get("name"));

        return entityManager.createQuery(query).getResultList();
    }
}
