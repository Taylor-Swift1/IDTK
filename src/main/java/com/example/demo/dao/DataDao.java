package com.example.demo.dao;

import com.example.demo.entity.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DataDao extends JpaRepository<Data,Integer>{
    @Query("select p from  Data p where p.num=?1 ")
    Data selectBySum(Integer num);

    @Query("select p from Data p where p.dt_data>?1 and p.dt_data<?2")
    List<Data> selectByDate(String date1,String date2);

}
