package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "hours_data1")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HoursData1 {
    @Id
    private String date;
    private Integer data;
}
