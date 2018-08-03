package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hours_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoursData {
    @Id
    private String date;
    private Integer in_dat;
    private Integer out_dat;
}
