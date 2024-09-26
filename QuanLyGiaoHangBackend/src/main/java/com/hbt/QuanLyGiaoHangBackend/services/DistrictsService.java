package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.Specification.DistrictSpecifications;
import com.hbt.QuanLyGiaoHangBackend.Specification.ProvicesSpecifications;
import com.hbt.QuanLyGiaoHangBackend.pojo.Districts;
import com.hbt.QuanLyGiaoHangBackend.pojo.Provinces;
import com.hbt.QuanLyGiaoHangBackend.repositories.DistrictsRepository;
import com.hbt.QuanLyGiaoHangBackend.repositories.ProvincesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictsService {
    @Autowired
    private DistrictsRepository districtsRepository;

    @Autowired
    private ProvincesRepository provincesRepository;

    public List<Districts> getDistricts(int id) {
        Provinces p = provincesRepository.findById(id).orElse(null);
        Specification<Districts> spec = DistrictSpecifications.getDistrictsByProvice(p);

        return districtsRepository.findAll(spec);
    }

    public com.hbt.QuanLyGiaoHangBackend.pojo.Districts getDistrictById(String id) {
        return districtsRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new IllegalArgumentException("District not found: " + id));
    }


}
