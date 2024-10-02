package com.hbt.QuanLyGiaoHangBackend.Specification;

import com.hbt.QuanLyGiaoHangBackend.pojo.Service;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import com.hbt.QuanLyGiaoHangBackend.pojo.Voucher;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class VoucherSpecifications {

//    public static Specification<Voucher> voucherActive(Boolean active) {
//        return (root, query, criteriaBuilder) ->{
//            Date today = new Date();  // Lấy ngày hiện tại
//
//            if (active != null && active) {
//                // Active vouchers: expiration_date after today
//                return criteriaBuilder.greaterThanOrEqualTo(root.get("expirationDate"), today);
//            } else {
//                // Inactive vouchers: expiration_date on or before today
//                return criteriaBuilder.lessThan(root.get("expirationDate"), today);
//            }
//        };
//
//    }


    public static Specification<Voucher> userActiveVouchers(Boolean active) {
        return (root, query, criteriaBuilder) -> {
            // Lấy ngày hiện tại
            Date today = new Date();

            // Điều kiện để kiểm tra voucher có active hay không (expirationDate sau ngày hiện tại)
            Predicate activeCondition = null;
            if (active != null && active) {
                activeCondition = criteriaBuilder.greaterThanOrEqualTo(root.get("expirationDate"), today);
            } else {
                activeCondition = criteriaBuilder.lessThan(root.get("expirationDate"), today);
            }

            return criteriaBuilder.and(activeCondition);

        };

    }
}