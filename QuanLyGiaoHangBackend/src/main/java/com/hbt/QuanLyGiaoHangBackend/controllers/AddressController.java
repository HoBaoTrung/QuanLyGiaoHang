package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.pojo.Districts;
import com.hbt.QuanLyGiaoHangBackend.pojo.Provinces;
import com.hbt.QuanLyGiaoHangBackend.repositories.DistrictsRepository;
import com.hbt.QuanLyGiaoHangBackend.services.DistrictsService;
import com.hbt.QuanLyGiaoHangBackend.services.ProvincesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/")
@RestController
public class AddressController {

    @Autowired
    private ProvincesService provincesService;

    @Autowired
    private DistrictsService districtsService;

    @GetMapping("public/getProvince/{name}/")
    public List<Provinces> getProvinces(@PathVariable("name") String name){

        if(name.equals("undefined")) name=null;
      
        return this.provincesService.getProvinces(name);
    }

    // get districts by provinceId
    @GetMapping("public/getDistricts/{id}/")
    public List<Districts> getDistricts(@PathVariable("id") int id){
        return this.districtsService.getDistricts(id);
    }

}
