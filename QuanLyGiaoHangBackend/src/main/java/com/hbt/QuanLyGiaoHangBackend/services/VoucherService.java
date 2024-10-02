package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.Specification.VoucherSpecifications;
import com.hbt.QuanLyGiaoHangBackend.dto.response.VoucherResponse;
import com.hbt.QuanLyGiaoHangBackend.mapper.VoucherMapper;
import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucher;
import com.hbt.QuanLyGiaoHangBackend.pojo.Voucher;
import com.hbt.QuanLyGiaoHangBackend.repositories.UserHaveVoucherRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.VoucherRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
@Transactional
@Service
public class VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private UserHaveVoucherRepository userHaveVoucherRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VoucherMapper voucherMapper;


    public void updateUserVoucher(UserHaveVoucher uv){
        userHaveVoucherRepository.save(uv);
    }

    public List<VoucherResponse> getUserVouchers() {
        return voucherMapper.toVoucherResponseList(userHaveVoucherRepository.findByUserId(userService.getCurrentUser().getId()));

    }

    public UserHaveVoucher getUserVoucherById(Long voucherId) {
        return userHaveVoucherRepository.findByUserIdAndVoucherId(userService.getCurrentUser().getId(), voucherId);
    }

    public void deleteUserVoucherById(Long voucherId) {
      
        try {
            userHaveVoucherRepository.delete(getUserVoucherById(voucherId));
        }
        catch (Exception e){System.out.println(e);}

    }

    public Page<Voucher> getAllVouchers(Map<String, String> params) {
        Specification<Voucher> spec = Specification.where(null);
        Boolean isActive=false;
        String pageParam =null;
        if(params!=null){
            pageParam=params.get("page");
            isActive= Boolean.valueOf(params.get("isActive"));
            spec = spec.and(VoucherSpecifications.userActiveVouchers(isActive));

            if (pageParam != null && !pageParam.isEmpty()) {

                Pageable pageable = PageRequest.of(Integer.parseInt(pageParam), 8, Sort.by(Sort.Direction.DESC, "id"));

                return this.voucherRepository.findAll(spec, pageable);

            }
        }
        return new PageImpl<>(this.voucherRepository.findAll());

    }
}

