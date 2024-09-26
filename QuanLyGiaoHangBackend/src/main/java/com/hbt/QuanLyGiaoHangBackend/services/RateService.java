package com.hbt.QuanLyGiaoHangBackend.services;

import com.hbt.QuanLyGiaoHangBackend.component.JwtService;
import com.hbt.QuanLyGiaoHangBackend.pojo.Rate;
import com.hbt.QuanLyGiaoHangBackend.pojo.RatePK;
import com.hbt.QuanLyGiaoHangBackend.repositories.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.Map;

@Service
public class RateService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private RateRepository rateRepository;

    @Autowired
    private JwtService jwtService;

    public Double getAveragePointForShipper(Long shipperId) {
        // Tạo CriteriaBuilder và CriteriaQuery
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> criteriaQuery = criteriaBuilder.createQuery(Double.class);

        // Định nghĩa root và các điều kiện
        Root<Rate> root = criteriaQuery.from(Rate.class);
        criteriaQuery.select(criteriaBuilder.avg(root.get("point")));

        // Áp dụng điều kiện shipperId
        criteriaQuery.where(criteriaBuilder.equal(root.get("id").get("shipperId"), shipperId));

        // Thực hiện truy vấn
        return entityManager.createQuery(criteriaQuery).getSingleResult();
    }

    private long getUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        return jwtService.getUserIDFromToken(jwt.getTokenValue());
    }

    public Integer getOwnerPoint(Map<String, String> params){
        Long userId =getUserId();
        long shipperId=Integer.parseInt(params.get("shipperId"));
        RatePK id = new RatePK(userId,shipperId);
        return this.rateRepository.findById(id).orElse(null).getPoint();
    }

    public void postOwnerPoint(Map<String, String> params){
        Long userId =getUserId();
        long shipperId=Integer.parseInt(params.get("shipperId"));
        RatePK id = new RatePK(userId,shipperId);
        Rate rate = rateRepository.findById(id).orElse(new Rate(id));
        rate.setPoint(Integer.parseInt(params.get("point")));
        this.rateRepository.save(rate);
    }
}

