package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.Rate;
import com.hbt.QuanLyGiaoHangBackend.pojo.RatePK;
import com.hbt.QuanLyGiaoHangBackend.pojo.Role;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, RatePK> {

}