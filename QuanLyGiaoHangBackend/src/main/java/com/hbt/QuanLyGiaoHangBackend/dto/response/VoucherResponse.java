package com.hbt.QuanLyGiaoHangBackend.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherResponse {
    Long id;
    String name;
    double value;
    Integer quantity;
    String measurement;
    Date expirationDate;
}
