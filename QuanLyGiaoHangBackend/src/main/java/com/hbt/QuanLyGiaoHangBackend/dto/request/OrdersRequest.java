package com.hbt.QuanLyGiaoHangBackend.dto.request;

import com.hbt.QuanLyGiaoHangBackend.pojo.Districts;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrdersRequest {
    Long id;
    String name;
    String image;
    int quantity;
    Districts toDistrictId;
    Boolean isPay;
}