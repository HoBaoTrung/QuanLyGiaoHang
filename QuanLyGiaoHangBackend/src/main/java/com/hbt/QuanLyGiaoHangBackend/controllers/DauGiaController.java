package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.dto.response.OrderDetailResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.services.DauGiaService;
import com.hbt.QuanLyGiaoHangBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/api/")
@RestController
public class DauGiaController {

    @Autowired
    private DauGiaService dauGiaService;

    @Autowired
    private UserService userService;

//    @GetMapping("shipper/viewDauGia/")
//    public DauGia getViewDauGia(@RequestParam Map<String, String> params){
//        DauGia o = this.dauGiaService.addOrUpdateDauGia(id,Long.parseLong(params.get("shipperId")),
//                Double.parseDouble(params.get("price")));
//        return o;
//    }

    @PostMapping("shipper/addDauGia/")
    public void addDauGia( @RequestParam Map<String, String> params){
        System.out.println(params);
        long shipperid = userService.getUserByID(Long.parseLong(params.get("userId"))).getShipper().getId();
        DauGia o = this.dauGiaService.addOrUpdateDauGia(Long.parseLong(params.get("productId")),
                shipperid,Double.parseDouble(params.get("price")));


    }
}
