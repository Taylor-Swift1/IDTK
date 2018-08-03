package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "year_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearData {
    @Id
    private String month;
    private Integer data;
}
