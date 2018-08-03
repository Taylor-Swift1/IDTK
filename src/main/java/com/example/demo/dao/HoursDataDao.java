package com.example.demo.dao;

import com.example.demo.entity.HoursData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoursDataDao extends JpaRepository<HoursData,Integer> {
    @Query("select p from HoursData p where date_format(p.date,'%Y-%m')=?1")
    List<HoursData> selectBymonth(String date);




}
