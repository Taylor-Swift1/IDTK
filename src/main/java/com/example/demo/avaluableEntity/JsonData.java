package com.example.demo.avaluableEntity;

import com.example.demo.entity.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonData {
    private Integer page;
    private Integer total;
    private Integer records;
    private String result;
    private String msg;
    List<Data> rows;
}
