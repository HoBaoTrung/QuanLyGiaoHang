package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.pojo.Proof;
import com.hbt.QuanLyGiaoHangBackend.pojo.Rate;
import com.hbt.QuanLyGiaoHangBackend.pojo.RatePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProofRepository extends JpaRepository<Proof, Long> {

}