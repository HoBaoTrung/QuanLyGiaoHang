package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.Districts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DistrictsRepository extends JpaRepository<Districts, Integer>, JpaSpecificationExecutor<Districts> {
}
