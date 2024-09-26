package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.Specification.ServiceSpecifications;
import com.hbt.QuanLyGiaoHangBackend.pojo.Category;
import com.hbt.QuanLyGiaoHangBackend.repositories.ServiceRepository;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeDelivierService {

    @Autowired
    private ServiceRepository serviceRepository;

    public com.hbt.QuanLyGiaoHangBackend.pojo.Service getServiceByName(String name) {
        return serviceRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + name));
    }

    public com.hbt.QuanLyGiaoHangBackend.pojo.Service getServiceById(String id) {
        return serviceRepository.findById(Integer.parseInt(id))
                .orElseThrow(() -> new IllegalArgumentException("Service not found: " + id));
    }

    public Page<com.hbt.QuanLyGiaoHangBackend.pojo.Service> getAllService(Map<String, String> params) {
        Specification<com.hbt.QuanLyGiaoHangBackend.pojo.Service>
                spec = Specification.where(null);
        Boolean isActive = Boolean.parseBoolean(params.get("getActiveService"));
        spec = spec.and(ServiceSpecifications.shipperActive(isActive));
        String pageParam = params.get("page");
        if (pageParam != null && !pageParam.isEmpty()) {

            Pageable pageable = PageRequest.of(Integer.parseInt(pageParam), 8, Sort.by(Sort.Direction.DESC, "id"));
            return this.serviceRepository.findAll(spec, pageable);
        }
        return new PageImpl<>(this.serviceRepository.findAll(spec));
    }
    public boolean addOrUpdateService(Map<String,String> params){

        com.hbt.QuanLyGiaoHangBackend.pojo.Service ser = serviceRepository
                .findById(Integer.parseInt(params.get("id"))).orElse(new com.hbt.QuanLyGiaoHangBackend.pojo.Service());

        String serviceName = params.get("serviceName");
        if(serviceName!=null){
            if(!serviceName.isEmpty())
            ser.setName(params.get("serviceName"));
        }

        String servicePrice = params.get("priceService");
        if(servicePrice!=null){
            if(!servicePrice.isEmpty())
                ser.setPrice(Double.parseDouble(params.get("priceService")));
        }

        String serviceActive = params.get("activeState");
        System.out.println(serviceActive);
        if(serviceActive!=null){

            if(!serviceActive.isEmpty()) {
                Boolean active = Boolean.parseBoolean(serviceActive);
                ser.setActive(active);
            }
        }

        try {
            serviceRepository.save(ser);
        }
        catch (HibernateException e){return false;}
        return true;
    }
}
