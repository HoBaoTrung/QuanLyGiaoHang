package com.hbt.QuanLyGiaoHangBackend.Specification;

import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class DauGiaSpecifications {

    public static Specification<DauGia> dauGiaBySelected(Boolean selected) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("selected"), selected);
    }

    public static Specification<DauGia> dauGiaByShipper(Shipper shipper) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("shipper"), shipper);
    }

    public static Specification<DauGia> dauGiaByProduct(Product p) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("product"), p);
    }

    public static Specification<DauGia> dauGiaBySelected(boolean p) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("selected"), p);
    }

    public static Specification<DauGia> productHasYear(int year) {
        return (root, query, criteriaBuilder) -> {
            Join<DauGia, Product> productJoin = root.join("product");
            return criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, productJoin.get("paymentDate")), year);
        };
    }

//    public static Specification<DauGia> groupByMonth() {
//        return (root, query, criteriaBuilder) -> {
//            query.groupBy(criteriaBuilder.function("MONTH", Integer.class, root.get("product").get("paymentDate")));
//            return null;
//        };
//    }
//
//    public static Specification<DauGia> groupByQuarter() {
//        return (root, query, criteriaBuilder) -> {
//            query.groupBy(criteriaBuilder.function("QUARTER", Integer.class, root.get("product").get("paymentDate")));
//            return null;
//        };
//    }

//    public static Specification<DauGia> groupByPeriodWithSum(String period) {
//        return (root, query, criteriaBuilder) -> {
//            query.groupBy(criteriaBuilder.function(period.toUpperCase(), Integer.class, root.get("product").get("paymentDate")));
//            query.multiselect(
//                    criteriaBuilder.function(period.toUpperCase(), Integer.class, root.get("product").get("paymentDate")),
//                    criteriaBuilder.sum(root.get("price"))
//            );
//            return null;
//        };
//    }
}
