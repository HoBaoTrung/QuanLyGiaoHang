package com.hbt.QuanLyGiaoHangBackend.services;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CommonService {
    public <T> List<T> convertStringToList(String input, Function<String, T> converter) {
        // Tách chuỗi bằng dấu phẩy và sử dụng converter để chuyển từng phần tử
        return Arrays.stream(input.split(","))
                .map(converter) // Chuyển đổi chuỗi con sang kiểu T
                .collect(Collectors.toList()); // Thu thập thành List<T>
    }

    public  <T> Set<T> convertToSet(Collection<T> collection) {
        return new HashSet<>(collection); // Chuyển Collection thành HashSet
    }
}
