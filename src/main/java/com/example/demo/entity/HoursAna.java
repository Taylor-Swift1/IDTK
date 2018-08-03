package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "hours_ana")
public class HoursAna {
    @Id
    private Integer id;
    private Integer in_dat;
    private Integer out_dat;
    private Integer last;
}
