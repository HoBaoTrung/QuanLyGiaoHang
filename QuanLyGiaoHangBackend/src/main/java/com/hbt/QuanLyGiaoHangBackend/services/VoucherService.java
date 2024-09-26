package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.dto.response.VoucherResponse;
import com.hbt.QuanLyGiaoHangBackend.mapper.VoucherMapper;
import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucher;
import com.hbt.QuanLyGiaoHangBackend.repositories.UserHaveVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private UserHaveVoucherRepository userHaveVoucherRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VoucherMapper voucherMapper;

    // Lấy thông tin người dùng hiện tại
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getCurrentUser().getId();
    }

    public void updateUserVoucher(UserHaveVoucher uv){
        userHaveVoucherRepository.save(uv);
    }

    public List<VoucherResponse> getUserVouchers() {
        return voucherMapper.toVoucherResponseList(userHaveVoucherRepository.findByUserId(getCurrentUserId()));

    }

    public UserHaveVoucher getUserVoucherById(Long voucherId) {
        return userHaveVoucherRepository.findByUserIdAndVoucherId(getCurrentUserId(), voucherId);
    }

    public void deleteUserVoucherById(Long voucherId) {
        System.out.println(voucherId);
        try {
            userHaveVoucherRepository.delete(getUserVoucherById(voucherId));
        }
        catch (Exception e){System.out.println(e);}

    }
}

