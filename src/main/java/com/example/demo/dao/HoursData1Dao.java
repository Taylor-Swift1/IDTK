package com.example.demo.dao;

import com.example.demo.entity.HoursData1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoursData1Dao extends JpaRepository<HoursData1,String> {
    @Query("select p from HoursData1 p where p.date>?1 and p.date <= ?2")
    List<HoursData1> findDataByDate(String date1,String date2);
}
