package com.example.demo.controller;

import com.example.demo.dao.*;
import com.example.demo.entity.HoursAna;
import com.example.demo.entity.HoursData;
import com.example.demo.entity.HoursData1;
import com.example.demo.entity.YearData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/")
public class ApiController {

    @Autowired
    HoursDataDao hoursDataDao;
    @Autowired
    YearDataDao yearDataDao;
    @Autowired
    DataDao dataDao;
    @Autowired
    HoursData1Dao hoursData1Dao;
    @Autowired
    HoursAnaDao hoursAnaDao;

    @GetMapping("/month")
    public Map month(@RequestParam Integer year , @RequestParam Integer month){
        String date=null;
        Map<String,List> map=new HashMap<>();
        if(month/10<1){
             date=""+year+"-"+"0"+month;
        }
        else{
             date=""+year+"-"+month;
        }
        List<HoursData> list=hoursDataDao.selectBymonth(date);
        map.put("month",list);
        List<HoursData> first=new ArrayList<>();
        List<HoursData> second=new ArrayList<>();
        List<HoursData> third=new ArrayList<>();
        int i=0;
        for(HoursData hoursData:list){
            i++;
            if(i<=10){
                first.add(hoursData);
            }
            else if(i<=20){
                second.add(hoursData);
            }
            else {
                third.add(hoursData);
            }
        }
        map.put("first",first);
        map.put("second",second);
        map.put("third",third);
        return map;
    }

    @GetMapping("/year")
    public List<YearData> year(@RequestParam String year){
        return yearDataDao.selectByYear(year);
    }

    @GetMapping("/day")
    public List<HoursData1> day(@RequestParam Integer year,@RequestParam Integer month,@RequestParam Integer day,@RequestParam Integer time1,@RequestParam Integer time2){
        String date1=year+"-"+month+"-"+day+" "+time1+":00:00";
        String date2=year+"-"+month+"-"+day+" "+time2+":00:00";
        List<HoursData1> list1=hoursData1Dao.findDataByDate(date1,date2);
        return list1;
    }

    @GetMapping("/today")
    public List<HoursAna> today(){
        return hoursAnaDao.findAll24();
    }
}
