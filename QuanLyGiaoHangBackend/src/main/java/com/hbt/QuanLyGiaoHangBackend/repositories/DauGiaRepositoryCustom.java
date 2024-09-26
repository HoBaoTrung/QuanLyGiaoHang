package com.hbt.QuanLyGiaoHangBackend.repositories;

import java.util.List;

public interface DauGiaRepositoryCustom {
    List<Object[]> getPriceByPeriodAndYear(int year, String period);
    List<Object[]> countSuccessProductByPeriodAndYear(int year, String period);
}
