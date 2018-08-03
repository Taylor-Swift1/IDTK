package com.example.demo.dao;

import com.example.demo.entity.HoursData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HoursDataDao extends JpaRepository<HoursData,Integer> {

}
