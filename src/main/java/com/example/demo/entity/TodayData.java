package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "today_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodayData {
    @Id
    private Integer num;
    private String sn;
    private String dt_data;
    private String dt_upload;
    private Integer in_dat;
    private Integer out_dat;
    private Integer bat_voltage;
    private Integer bat_percent;
    private Boolean charge_state;
    private Boolean focus_state;
}
