package com.hbt.QuanLyGiaoHangBackend.dto.request;

import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGiaPK;
import lombok.Data;

import java.util.Set;

@Data
public class PaymentRequest {
    private int voucherId;
    private Set<DauGia> dauGiaSet;
    private DauGia dauGiaSelected;


}
