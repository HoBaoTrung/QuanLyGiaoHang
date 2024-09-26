package com.hbt.QuanLyGiaoHangBackend.mapper;

import com.hbt.QuanLyGiaoHangBackend.dto.response.OrderDetailResponse;
import com.hbt.QuanLyGiaoHangBackend.dto.response.OrdersResponse;
import com.hbt.QuanLyGiaoHangBackend.exception.AppException;
import com.hbt.QuanLyGiaoHangBackend.exception.ErrorCode;
import com.hbt.QuanLyGiaoHangBackend.pojo.Category;
import com.hbt.QuanLyGiaoHangBackend.pojo.DauGia;
import com.hbt.QuanLyGiaoHangBackend.pojo.Product;
import com.hbt.QuanLyGiaoHangBackend.pojo.User;
import com.hbt.QuanLyGiaoHangBackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Component
public class OrderMapper {

    private double getBasicPrice(Product product){
        return  product.getQuantity()*(

                product.getCategorySet().stream()
                        .mapToDouble(Category::getPrice)
                        .sum()

                        + product.getServiceId().getPrice()
        );
    }

    OrdersResponse toOrdersResponse(Product product){
        double basicPrice =getBasicPrice(product);

        OrdersResponse o =new OrdersResponse(product.getId(), product.getName(),
                product.getImage(), product.getQuantity(), product.getToDistrictId(),
                product.getPaymentDate()!=null,basicPrice);
        return o;
    }


    public List<OrdersResponse> toOrdersResponseList(List<Product> DauGias){
        int size=0;
        if(DauGias!=null)size= DauGias.size();
        List<OrdersResponse> list = new ArrayList(size);
        Iterator var3 = DauGias.iterator();

        while(var3.hasNext()) {
            Product d = (Product)var3.next();
            list.add(this.toOrdersResponse(d));
        }

        return list;

    }
    public OrderDetailResponse toOrderDetailResponse(Product p){
        boolean isShip = p.getProof()!=null?true:false;
        double basicPrice =getBasicPrice(p);
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse(p.getId(), p.getName(), p.getQuantity(),
                p.getNote(),p.getImage(),isShip, p.getCategorySet(),
                p.getFromDistrictId(),p.getToDistrictId(),p.getServiceId(),
                basicPrice,p.getDauGiaSet());

        return orderDetailResponse;
    }
}


