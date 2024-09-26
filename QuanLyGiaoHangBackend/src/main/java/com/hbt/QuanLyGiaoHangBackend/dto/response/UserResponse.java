package com.hbt.QuanLyGiaoHangBackend.dto.response;

import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
     Long id;
     String username;
     String avatar;
     String phone;
     String email;
     Long shipperId;
     boolean isShipperActive;
     Set<Role> roles;
}