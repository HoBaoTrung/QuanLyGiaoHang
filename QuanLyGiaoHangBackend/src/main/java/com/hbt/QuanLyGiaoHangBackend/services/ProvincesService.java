package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.pojo.Provinces;
import com.hbt.QuanLyGiaoHangBackend.repositories.ProvincesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.hbt.QuanLyGiaoHangBackend.Specification.ProvicesSpecifications;

import java.util.List;

@Service
public class ProvincesService {
    @Autowired
    private ProvincesRepository provincesRepository;

    public List<Provinces> getProvinces(String name) {
        Specification<Provinces> spec = null;
        if(name!=null && !name.isEmpty())
            spec= ProvicesSpecifications.haNoiOrHoChiMinh();
        return provincesRepository.findAll(spec);
    }


}
