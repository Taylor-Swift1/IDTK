package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@Table(name="data")
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer num;
    private String sn;
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String dt_data;
//    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private String dt_upload;
    private Integer in_dat;
    private Integer out_dat;
    private Integer bat_voltage;
    private Integer bat_percent;
    private Boolean charge_state;
    private Boolean focus_state;
}
