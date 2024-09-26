package com.hbt.QuanLyGiaoHangBackend.dto.response;

import com.hbt.QuanLyGiaoHangBackend.pojo.Comment;
import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipperResponse {
     Long id;
     String username;
     String avatar;
     String phone;
     String email;
     boolean active;
     long totalOrder;
     double totalPrice;
     String cmnd;
}