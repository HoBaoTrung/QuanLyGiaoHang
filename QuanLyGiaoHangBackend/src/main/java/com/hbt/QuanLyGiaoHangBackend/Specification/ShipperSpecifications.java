package com.hbt.QuanLyGiaoHangBackend.Specification;

import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import org.springframework.data.jpa.domain.Specification;

public class ShipperSpecifications {

    public static Specification<Shipper> shipperActive(Boolean active) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("active"), active);
    }


}
