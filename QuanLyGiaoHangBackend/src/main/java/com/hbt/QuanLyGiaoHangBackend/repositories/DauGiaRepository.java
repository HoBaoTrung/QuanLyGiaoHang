package com.hbt.QuanLyGiaoHangBackend.repositories;

import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGiaPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

@Repository
public interface DauGiaRepository extends JpaRepository<DauGia, DauGiaPK>, DauGiaRepositoryCustom  {
    @Query("SELECT CASE WHEN COUNT(dg) > 0 THEN true ELSE false END " +
            "FROM DauGia dg WHERE dg.id.productId = :productId " +
            "AND dg.shipper.user.username = :username " +
            "AND dg.selected = true")
    boolean existsByProductIdAndShipperWithSelected(@Param("productId") Long productId, @Param("username") String username);
    List<DauGia> findAll(Specification<DauGia> spec);
    Page<DauGia> findAll(Specification<DauGia> spec, Pageable pageable);

}
