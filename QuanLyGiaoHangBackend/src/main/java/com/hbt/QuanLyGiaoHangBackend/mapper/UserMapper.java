package com.hbt.QuanLyGiaoHangBackend.mapper;

import com.hbt.QuanLyGiaoHangBackend.dto.request.RegisterRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.response.RoleResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.UserResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Component
public class UserMapper {

     public UserResponse toUserResponse(User user){
          boolean isShipperActive=false;
          Long shipperId =null;
          if(user.getShipper()!=null){
               isShipperActive= user.getShipper().getActive();
               shipperId = user.getShipper().getId();
          }
          UserResponse u  = new UserResponse(user.getId(), user.getUsername(), user.getAvatar(), user.getPhone(),
                  user.getEmail(),shipperId,isShipperActive,user.getRoles());
          return u;
     }

//    @Mapping(target = "roles", ignore = true)
//    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
