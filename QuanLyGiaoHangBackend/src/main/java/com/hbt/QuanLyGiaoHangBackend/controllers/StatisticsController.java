package com.hbt.QuanLyGiaoHangBackend.controllers;

import com.hbt.QuanLyGiaoHangBackend.services.DauGiaService;
import com.hbt.QuanLyGiaoHangBackend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
public class StatisticsController {

    @Autowired
    private DauGiaService dauGiaService;

    @Autowired
    private ProductService productService;

    @PostMapping("price-by-period/")
    public ResponseEntity<List<Map<String, Object>>> getPriceByMonth(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(dauGiaService.getPriceByPeriod(params), HttpStatus.OK);
    }

    @PostMapping("count-by-period/")
    public ResponseEntity<List<Map<String, Object>>> getCountByPeriod(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(productService.countProductsByMonth(params), HttpStatus.OK);
    }

    @PostMapping("count-by-service/")
    public ResponseEntity<List<Map<String, Object>>> getCountProductsByService(@RequestParam Map<String, String> params) {
        return new ResponseEntity<>(productService.countProductsByService(params), HttpStatus.OK);
    }
}

