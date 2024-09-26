package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.Specification.DauGiaSpecifications;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGiaPK;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.repositories.DauGiaRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DauGiaService {


    @Autowired
    private DauGiaRepository dauGiaRepository;

    public DauGia addOrUpdateDauGia(Long productId, Long shipperId, Double price) {
        // Tạo khóa chính tổng hợp
        DauGiaPK id = new DauGiaPK(productId, shipperId);

        // Tìm kiếm theo khóa chính, nếu không thấy thì tạo mới
        DauGia dauGia = dauGiaRepository.findById(id).orElse(new DauGia(id, price, false));

        // Cập nhật giá nếu đối tượng đã tồn tại
        dauGia.setPrice(price);

        // Lưu đối tượng vào cơ sở dữ liệu
        return dauGiaRepository.save(dauGia);

    }

    public void updateDauGia(DauGia d) {
        dauGiaRepository.save(d);
    }

    public void deleteDauGia(Product product) {
        Specification<DauGia> specDauGia = Specification.where(null);
        specDauGia = specDauGia.and(DauGiaSpecifications.dauGiaBySelected(false))
                .and(DauGiaSpecifications.dauGiaByProduct(product));
        List<DauGia> listDauGia = this.dauGiaRepository.findAll(specDauGia);
        if (listDauGia != null && !listDauGia.isEmpty()) {
            dauGiaRepository.deleteAllInBatch(listDauGia);
        }
    }

    public List<Map<String, Object>> getPriceByPeriod(Map<String, String> params) {

        String yearString = params.get("year");

        if(yearString.isEmpty() || yearString==null){
            Calendar calendar = Calendar.getInstance();
            yearString = calendar.get(Calendar.YEAR)+"";
        }
        int year = Integer.parseInt(yearString);
        String period = params.get("period");

        List<Object[]> resultList = dauGiaRepository.getPriceByPeriodAndYear(year,period);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : resultList) {
            Map<String, Object> map = new HashMap<>();
            map.put(period, row[0]);  // Period
            map.put("price", row[1]);  // Total price
            result.add(map);
        }

        return result;
    }


}
