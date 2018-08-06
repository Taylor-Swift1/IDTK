package com.example.demo.dao;

import com.example.demo.entity.YearData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface YearDataDao extends JpaRepository<YearData,Integer> {
    @Query("select p from YearData p where date_format(p.month,'%Y')=?1")
    List<YearData> selectByYear(String year);
}
