package com.hbt.QuanLyGiaoHangBackend.mapper;

import com.hbt.QuanLyGiaoHangBackend.dto.response.OrdersResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.RoleResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.VoucherResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucher;
import com.hbt.QuanLyGiaoHangBackend.pojo.Voucher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class VoucherMapper {

    VoucherResponse toVoucherResponse(UserHaveVoucher v) {
        VoucherResponse rp = new VoucherResponse();
        rp.setId(v.getVoucher().getId());
        rp.setName(v.getVoucher().getName());
        rp.setValue(v.getVoucher().getValue());
        rp.setMeasurement(v.getVoucher().getMeasurement());
        rp.setExpirationDate(v.getVoucher().getExpirationDate());
        rp.setQuantity(v.getQuantity());
        return rp;
    }

    public List<VoucherResponse> toVoucherResponseList(List<UserHaveVoucher> vs){
        int size=0;
        if(vs!=null)size= vs.size();
        List<VoucherResponse> list = new ArrayList(size);
        Iterator var3 = vs.iterator();

        while(var3.hasNext()) {
            UserHaveVoucher d = (UserHaveVoucher)var3.next();
            list.add(this.toVoucherResponse(d));
        }

        return list;

    }
}