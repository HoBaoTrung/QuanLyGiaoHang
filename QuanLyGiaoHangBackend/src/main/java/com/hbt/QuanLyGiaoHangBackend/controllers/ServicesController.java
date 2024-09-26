package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.pojo.Service;
import com.hbt.QuanLyGiaoHangBackend.services.TypeDelivierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/")
@RestController
public class ServicesController {

    @Autowired
    private TypeDelivierService typeDelivierService;


    @PostMapping("public/getAllService/")
    public PagedModel<Service> getAllService(@RequestParam Map<String,String> params, PagedResourcesAssembler assembler){
        return assembler.toModel(this.typeDelivierService.getAllService(params));
    }

    @PostMapping("admin/addOrUpdateService/")
    public void addOrUpdateCategory(@RequestParam Map<String,String> params){

        if(params!=null)  this.typeDelivierService.addOrUpdateService(params);
    }

}
