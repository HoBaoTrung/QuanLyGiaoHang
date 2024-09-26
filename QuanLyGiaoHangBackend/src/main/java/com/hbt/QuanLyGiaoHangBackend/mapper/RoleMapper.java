package com.hbt.QuanLyGiaoHangBackend.mapper;

import com.hbt.QuanLyGiaoHangBackend.dto.request.RoleRequest;
import com.hbt.QuanLyGiaoHangBackend.dto.response.RoleResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RoleMapper {
//    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role){
        RoleResponse rp= new RoleResponse();
        rp.setId(role.getId());
        rp.setName(role.getName());
        return rp;
    };
}