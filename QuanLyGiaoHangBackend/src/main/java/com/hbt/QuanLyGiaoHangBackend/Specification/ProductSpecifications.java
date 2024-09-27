package com.hbt.QuanLyGiaoHangBackend.Specification;

import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

public class ProductSpecifications {

    public static Specification<Product> productByShipper(User user, Map<String, String> params) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<DauGia> dauGiaRoot = subquery.from(DauGia.class);
            subquery.select(dauGiaRoot.get("product").get("id"));
            subquery.where(
                    criteriaBuilder.equal(dauGiaRoot.get("shipper").get("id"), user.getShipper().getId())
            );

            // Main query condition based on statusParam
            Predicate mainCondition = null;

            String statusParam = params.get("status");
            if (statusParam != null && !statusParam.isEmpty()) {
                mainCondition = criteriaBuilder.and(
                        criteriaBuilder.not(root.get("id").in(subquery)),
                        criteriaBuilder.notEqual(root.get("user").get("id"), user.getId()),
                        criteriaBuilder.isNull(root.get("paymentDate"))
                );
            }

            return mainCondition;
        };
    }
    public static Specification<Product> productByService(List<Integer> serviceIds) {
        return (root, query, criteriaBuilder) ->{
            Predicate serviceIdPredicate = root.get("serviceId").get("id").in(serviceIds);
            return criteriaBuilder.and(serviceIdPredicate);
        };
    }
    public static Specification<Product> productByProvince(Integer provinceId, boolean isSender) {
        return (root, query, criteriaBuilder) ->{
            String status = "fromDistrictId";
            if(!isSender){status = "toDistrictId";}
            return root.get(status).get("provinceId").get("id").in(provinceId);
        };

    }


    public static Specification<Product> findByUser(User currentUser) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("user"), currentUser);
    }

    public static Specification<Product> findByPaymentDate(Boolean isPay) {
        return (root, query, criteriaBuilder) -> {
            if (isPay) {
                // Nếu isPay=true, lấy tất cả các product có paymentDate khác null
                return criteriaBuilder.isNotNull(root.get("paymentDate"));
            } else {
                // Nếu isPay=false, lấy tất cả các product không có paymentDate (null)
                return criteriaBuilder.isNull(root.get("paymentDate"));
            }
        };
    }

    public static Specification<Product> hasYear(int year) {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(criteriaBuilder.function("YEAR", Integer.class, root.get("paymentDate")), year);
        };
    }

}
