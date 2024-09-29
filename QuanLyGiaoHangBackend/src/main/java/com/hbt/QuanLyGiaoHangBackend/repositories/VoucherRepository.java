package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.Service;
import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucher;
import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucherPK;
import com.hbt.QuanLyGiaoHangBackend.pojo.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoucherRepository extends JpaRepository<Voucher, Long> {
    Page<Voucher> findAll(Specification<Voucher> specVoucher, Pageable pageable);
    List<Voucher> findAll(Specification<Voucher> specVoucher);
}
