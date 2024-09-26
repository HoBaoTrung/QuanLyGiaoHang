package com.hbt.QuanLyGiaoHangBackend.mapper;

import com.hbt.QuanLyGiaoHangBackend.dto.response.DauGiaResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.OrderDetailResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.OrdersResponse;
import com.hbt.QuanLyGiaoHangBackend.pojo.Category;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class DauGiaMapper {

    DauGiaResponse toDauGiaResponse( DauGia d){
        Product product = d.getProduct();
        Set<Category> setCate= product.getCategorySet();
        double basicPrice = product.getQuantity()*(

                setCate.stream()
                .mapToDouble(Category::getPrice)
                .sum()

                + product.getServiceId().getPrice()
        );

        DauGiaResponse o =new DauGiaResponse(product.getId(), product.getName(),
                product.getImage(), product.getQuantity(), product.getToDistrictId(),
                product.getPaymentDate()!=null, basicPrice, d.getPrice());
        return o;
    }


    public List<DauGiaResponse> toDauGiaResponseList(List<DauGia> DauGias){
        int size=0;
        if(DauGias!=null)size= DauGias.size();
        List<DauGiaResponse> list = new ArrayList(size);
        Iterator var3 = DauGias.iterator();

        while(var3.hasNext()) {
            DauGia d = (DauGia)var3.next();
            list.add(this.toDauGiaResponse(d));
        }

        return list;

    }
//    public OrderDetailResponse toOrderDetailResponse(Product p){
//        OrderDetailResponse orderDetailResponse = new OrderDetailResponse(p.getId(), p.getName(), p.getQuantity(),
//                p.getNote(),p.getImage(),p.getIsShip(),p.getCreatedDate(),p.getPaymentDate(), p.getCategorySet(),
//                p.getFromDistrictId(),p.getToDistrictId(),p.getServiceId(),p.getUser(),p.getDauGiaSet());
//
//        return orderDetailResponse;
//    }
}


