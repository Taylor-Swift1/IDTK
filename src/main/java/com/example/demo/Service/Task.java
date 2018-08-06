package com.example.demo.Service;


import com.example.demo.avaluableEntity.JsonData;
import com.example.demo.avaluableEntity.TodayJsonData;
import com.example.demo.dao.*;
import com.example.demo.entity.*;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class Task {

    @Autowired
    DataDao dataDao;
    @Autowired
    TodayDataDao todayDataDao;
    @Autowired
    HoursAnaDao hoursAnaDao;
    @Autowired
    HoursDataDao hoursDataDao;
    @Autowired
    YearDataDao yearDataDao;
    @Autowired
    HoursData1Dao hoursData1Dao;

    @Scheduled(cron = "0 57 23 ? * *")
//@Scheduled(cron = "0/20 * *  * * ? ")
    public void scheduled() {
        URL url1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String url = "http://webservice.ywacn.com/dataport.aspx?cmd=getdatalist&password=1&sn=CA1C71DD&type=DAT&dt=" + date;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLConnection urlConnection = null;
        try {
            urlConnection = url1.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = null;
        try {
            s = inputStream.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonData jsonData = new Gson().fromJson(s, JsonData.class);
        List<Data> data = jsonData.getRows();
        for (Data data1 : data) {
            dataDao.save(data1);
        }
        String date1=simpleDateFormat.format(new Date());
        String date2=date1+" "+"00:00:00";
        String date3=date1+" "+"23:59:59";
        List<Data> datas=dataDao.selectByDate(date2,date3);
        Integer in_sum=0;
        Integer out_sum=0;
        for(Data data1:datas){
            in_sum+=data1.getIn_dat();
            out_sum+=data1.getOut_dat();
        }
        hoursDataDao.save(new HoursData(date1,in_sum,out_sum));

        SimpleDateFormat month=new SimpleDateFormat("yyyy-MM");
        String date4=month.format(new Date());
        List<HoursData> list=hoursDataDao.selectBymonth(date4);
        Integer in_data=0;
        Integer out_data=0;
        for(HoursData hoursData:list){
            in_data+=hoursData.getIn_dat();
            out_data+=hoursData.getOut_dat();
            System.out.println(in_data);
            System.out.println(out_data);
        }
        date4+="-01";
        yearDataDao.save(new YearData(date4,in_data>out_data?in_data:out_data));

        for(int i=1;i<25;i++){
            String time=date+" "+(i%24)+":00:00";
            String time2=date+" "+(i-1)+":00:00";
            List<Data> data1=dataDao.selectByDate(time2,time);
            Integer in_dat=0;
            Integer out_dat=0;
            for(Data data2:data1){
                in_dat+=data2.getIn_dat();
                out_dat+=data2.getOut_dat();
            }
            HoursData1 hoursData1=new HoursData1(time,in_dat,out_dat);
            hoursData1Dao.save(hoursData1);
        }

    }

    @Scheduled(cron = "0/59 * *  * * ? ")
    public void scheduled2() {

        URL url1 = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        String url = "http://webservice.ywacn.com/dataport.aspx?cmd=getdatalist&password=1&sn=CA1C71DD&type=DAT&dt=" + date;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        URLConnection urlConnection = null;
        try {
            urlConnection = url1.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader inputStream = null;
        try {
            inputStream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = null;
        try {
            s = inputStream.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TodayJsonData jsonData = new Gson().fromJson(s, TodayJsonData.class);
        List<TodayData> todayData = jsonData.getRows();
        for (TodayData data1 : todayData) {
            todayDataDao.save(data1);
        }
        for(int i=1;i<25;i++){
            try{
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                String date1=simpleDateFormat1.format(new Date());
                String date2=date1+" "+(i-1)+":00:00";
                String date3=date1+" "+(i-1)+":59:59";
                List<TodayData> todayDatas=todayDataDao.findByDt_data(date2,date3);
                Integer in_sum=0;
                Integer out_sum=0;
                for(TodayData todayData1:todayDatas){
                    in_sum+=todayData1.getIn_dat();
                    out_sum+=todayData1.getOut_dat();
                }
                hoursAnaDao.save(new HoursAna(i,in_sum,out_sum,in_sum-out_sum));
            }catch (Exception e){
                hoursAnaDao.save(new HoursAna(i,0,0,0));
            }
        }
        Integer l=0;
        Integer in=0;
        Integer out=0;
        List<HoursAna> hoursAnas=hoursAnaDao.findAll24();
        for(HoursAna hoursAna:hoursAnas){
            l+=hoursAna.getLast();
            in+=hoursAna.getIn_dat();
            out+=hoursAna.getOut_dat();
        }
        hoursAnaDao.save(new HoursAna(25,in,out,l));
    }
}
