package com.hbt.QuanLyGiaoHangBackend.mapper;

import com.hbt.QuanLyGiaoHangBackend.dto.response.ShipperResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.UserResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ShipperMapper {

     public ShipperResponse toShipperResponse(Shipper s){
          Set<DauGia> items = s.getDauGiaSet();
          long countTotalOrderSelected = items.stream().filter(DauGia::getSelected).count();
          double totalPriceSelected =items.stream().filter(DauGia::getSelected).mapToDouble(DauGia::getPrice).sum();
          ShipperResponse u  = new ShipperResponse(s.getId(), s.getUser().getUsername(), s.getUser().getAvatar(),
                  s.getUser().getPhone(),s.getUser().getEmail(),s.getActive(),countTotalOrderSelected,totalPriceSelected,s.getCmnd());

          return u;
     }

}
