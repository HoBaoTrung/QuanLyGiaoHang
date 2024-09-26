package com.hbt.QuanLyGiaoHangBackend.Specification;
import com.hbt.QuanLyGiaoHangBackend.pojo.Districts;
import com.hbt.QuanLyGiaoHangBackend.pojo.Provinces;
import org.springframework.data.jpa.domain.Specification;

public class DistrictSpecifications {

    public static  Specification<Districts> getDistrictsByProvice(Provinces p) {
        return (root, query, criteriaBuilder) ->criteriaBuilder.equal( root.get("provinceId"),p);

    }


}
