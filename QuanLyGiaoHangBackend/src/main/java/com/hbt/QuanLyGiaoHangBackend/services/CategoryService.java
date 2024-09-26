package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.pojo.Category;
import com.hbt.QuanLyGiaoHangBackend.repositories.CategoryRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Value("${PAGE_SIZE}")
    private int pageSize;

    public Category getCateByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + name));
    }

    public Category getCateById(String id) {
        return categoryRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));
    }

    public boolean addOrUpdateCate(Map<String,String> params){
        Category cate = categoryRepository.findById(Integer.parseInt(params.get("id"))).orElse(new Category());
        if(!params.get("cateName").isEmpty() && params.get("cateName")!=null)
            cate.setName(params.get("cateName"));
        cate.setPrice(Double.parseDouble(params.get("priceCate")));
        try {
            categoryRepository.save(cate);
        }
        catch (HibernateException e){return false;}
        return true;
    }

    public void deleteCate(int id){
        Category cate = categoryRepository.findById(id).orElse(null);
        this.categoryRepository.delete(cate);
    }

    public List<Category> getAllCateById(List<Integer> id){
        return categoryRepository.findAllById(id);
    }

    public Page<Category> getAllCate(Map<String,String> params){
        int page = 0;
        Pageable pageable = null;
        if (params != null) {
            String pageParam = params.get("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                page = Integer.parseInt(pageParam) ; // JPA page index starts from 0
                pageable =  PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "id"));
                return this.categoryRepository.findAll(pageable);
            }
        }
// Trường hợp không có pageParam thì lấy tất cả Category mà không phân trang
        return new PageImpl<>(this.categoryRepository.findAll());

    }

}
