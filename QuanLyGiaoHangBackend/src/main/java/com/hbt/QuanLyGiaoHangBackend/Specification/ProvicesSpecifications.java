package com.hbt.QuanLyGiaoHangBackend.Specification;
import com.hbt.QuanLyGiaoHangBackend.pojo.Provinces;
import org.springframework.data.jpa.domain.Specification;
public class ProvicesSpecifications {

    public static  Specification<Provinces> haNoiOrHoChiMinh() {
        return (root, query, criteriaBuilder) ->
                root.get("provinceName").in("Hà Nội", "Hồ Chí Minh");
    }


}
