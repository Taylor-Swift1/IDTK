package com.example.demo.dao;

import com.example.demo.entity.TodayData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface TodayDataDao extends JpaRepository<TodayData,Integer> {
    @Query("select p from TodayData p where p.dt_data> ?1 and p.dt_data < ?2")
    List<TodayData> findByDt_data(String date1,String date2);
}
