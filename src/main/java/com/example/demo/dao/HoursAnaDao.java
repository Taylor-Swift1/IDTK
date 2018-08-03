package com.example.demo.dao;

import com.example.demo.entity.HoursAna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HoursAnaDao extends JpaRepository<HoursAna,Integer> {
    @Query("select p from HoursAna p where p.id>=1 and p.id<=24")
    List<HoursAna> findAll24();
}
