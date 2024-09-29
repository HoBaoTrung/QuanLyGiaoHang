package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucher;
import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucherPK;
import com.hbt.QuanLyGiaoHangBackend.pojo.Voucher;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UserHaveVoucherRepository extends JpaRepository<UserHaveVoucher, UserHaveVoucherPK> {

    List<UserHaveVoucher> findByUserId(Long userId);

    UserHaveVoucher findByUserIdAndVoucherId(Long userId, Long voucherId);



}
