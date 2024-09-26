package com.hbt.QuanLyGiaoHangBackend.dto.response;

import com.hbt.QuanLyGiaoHangBackend.pojo.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;
    String name;
    Integer quantity;
    String note;
    String image;
    Boolean isShip;
    Set<Category> categorySet;
    Districts fromDistrictId;
    Districts toDistrictId;
    Service serviceId;
    double basicPrice;
    Set<DauGia> dauGiaSet;
}