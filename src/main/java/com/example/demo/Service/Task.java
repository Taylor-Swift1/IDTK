package com.example.demo.Service;


import com.example.demo.avaluableEntity.JsonData;
import com.example.demo.avaluableEntity.TodayJsonData;
import com.example.demo.dao.DataDao;
import com.example.demo.dao.HoursAnaDao;
import com.example.demo.dao.HoursDataDao;
import com.example.demo.dao.TodayDataDao;
import com.example.demo.entity.Data;
import com.example.demo.entity.HoursAna;
import com.example.demo.entity.HoursData;
import com.example.demo.entity.TodayData;
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

    @Scheduled(cron = "0 57 23 ? * *")
//@Scheduled(cron = "0/40 * *  * * ? ")
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
        Integer last=0;
        Integer in_sum=0;
        Integer out_sum=0;
        List<HoursAna> hoursAnas=hoursAnaDao.findAll();
        for(HoursAna hoursAna:hoursAnas){
            last+=hoursAna.getLast();
            in_sum+=hoursAna.getIn_dat();
            out_sum+=hoursAna.getOut_dat();
        }
        hoursAnaDao.save(new HoursAna(25,in_sum,out_sum,last));
    }
}
