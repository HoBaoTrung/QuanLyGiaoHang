package com.hbt.QuanLyGiaoHangBackend.Specification;

import com.hbt.QuanLyGiaoHangBackend.pojo.Service;
import org.springframework.data.jpa.domain.Specification;

public class ServiceSpecifications {

    public static Specification<Service> shipperActive(Boolean active) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("isActive"), active);
    }


}
