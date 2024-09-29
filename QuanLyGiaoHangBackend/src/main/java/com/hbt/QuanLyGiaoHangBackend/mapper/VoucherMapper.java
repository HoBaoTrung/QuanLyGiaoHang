package com.hbt.QuanLyGiaoHangBackend.mapper;

import com.hbt.QuanLyGiaoHangBackend.dto.response.OrdersResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.RoleResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.VoucherResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import com.hbt.QuanLyGiaoHangBackend.pojo.UserHaveVoucher;
import com.hbt.QuanLyGiaoHangBackend.pojo.Voucher;
import com.hbt.QuanLyGiaoHangBackend.repositories.UserHaveVoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class VoucherMapper {

    @Autowired
    private UserHaveVoucherRepository userHaveVoucherRepository;

    VoucherResponse toVoucherResponse(Voucher v, int quantity) {
        VoucherResponse rp = new VoucherResponse();
        rp.setId(v.getId());
        rp.setName(v.getName());
        rp.setValue(v.getValue());
        rp.setMeasurement(v.getMeasurement());
        rp.setExpirationDate(v.getExpirationDate());
        rp.setQuantity(quantity);
        return rp;
    }

    public List<VoucherResponse> toVoucherResponseList(List<UserHaveVoucher> vs){
        int size=0;
        if(vs!=null)size= vs.size();
        List<VoucherResponse> list = new ArrayList(size);
        Iterator var3 = vs.iterator();
        LocalDate currentDate = LocalDate.now();
        while(var3.hasNext()) {
            UserHaveVoucher d = (UserHaveVoucher)var3.next();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate expirationLocalDate = LocalDate.parse(d.getVoucher().getExpirationDate().toString(), formatter);

            if (expirationLocalDate.isBefore(currentDate)) {
                userHaveVoucherRepository.delete(d);
            } else {
                list.add(this.toVoucherResponse(d.getVoucher(), d.getQuantity()));
            }

        }

        return list;

    }
}