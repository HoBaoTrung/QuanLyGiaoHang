package com.hbt.QuanLyGiaoHangBackend.controllers;
import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucher;
import com.hbt.QuanLyGiaoHangBackend.pojo.Voucher;
import com.hbt.QuanLyGiaoHangBackend.services.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping("admin/vouchers/")
    public PagedModel<Voucher> getAllVouchers(@RequestParam Map<String,String> params, PagedResourcesAssembler assembler) {
        // Trả về danh sách voucher cho admin quản lý

        return  assembler.toModel(voucherService.getAllVouchers(params));

    }

    @GetMapping("customer/vouchers/")
    public ResponseEntity<List<?>> getMyVouchers() {

        // Trả về danh sách voucher của người dùng hiện tại
        List<?> userVouchers = voucherService.getUserVouchers();
        return ResponseEntity.ok(userVouchers);
    }

    // Lấy voucher cụ thể của user hiện tại
    @GetMapping("customer/vouchers/my/{voucherId}")
    public ResponseEntity<UserHaveVoucher> getMyVoucher(@PathVariable Long voucherId) {

        // Kiểm tra xem người dùng có sở hữu voucher này không
        UserHaveVoucher userHaveVoucher = voucherService.getUserVoucherById(voucherId);

        if (userHaveVoucher != null) {
            return ResponseEntity.ok(userHaveVoucher);
        } else {
            return ResponseEntity.status(403).body(null); // Trả về lỗi nếu không sở hữu
        }
    }


}
