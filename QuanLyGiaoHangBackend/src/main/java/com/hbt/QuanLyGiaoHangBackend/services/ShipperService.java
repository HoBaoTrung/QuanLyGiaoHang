package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.Specification.ShipperSpecifications;
import com.hbt.QuanLyGiaoHangBackend.pojo.Shipper;
import com.hbt.QuanLyGiaoHangBackend.repositories.ShipperRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShipperService {


    @Autowired
    private ShipperRepository shipperRepository;

    public void updateActiveStatus(Long id, boolean active) {
        Shipper shipper = shipperRepository.findById(id).orElse(null);
        shipper.setActive(active);
        shipperRepository.save(shipper);
    }

    public Page<Shipper> getAllShipper( Map<String, String> params){

        Integer page = Integer.parseInt(params.get("page"));
        Integer pageSize = Integer.parseInt(params.get("pageSize"));
        boolean isActive = Boolean.parseBoolean((params.get("active")));
        Specification<Shipper> specShipper = Specification.where(null);
        specShipper=specShipper.and(ShipperSpecifications.shipperActive(isActive));
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "id"));

        return shipperRepository.findAll(specShipper,pageable);
    }

}
