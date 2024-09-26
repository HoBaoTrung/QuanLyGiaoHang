package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.pojo.Category;
import com.hbt.QuanLyGiaoHangBackend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/api/")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping("public/getAllCate/")
    public PagedModel<Category> getAllService(@RequestParam Map<String,String> params, PagedResourcesAssembler assembler){

        return assembler.toModel(this.categoryService.getAllCate(params));
    }


    @PostMapping("admin/addOrUpdateCategory/")
    public void addOrUpdateCategory(@RequestParam Map<String,String> params){
        this.categoryService.addOrUpdateCate(params);
    }

    @DeleteMapping("admin/deleteCategory/{id}")
    public void deleteCategory(@PathVariable("id") int id){

        this.categoryService.deleteCate(id);
    }



}
