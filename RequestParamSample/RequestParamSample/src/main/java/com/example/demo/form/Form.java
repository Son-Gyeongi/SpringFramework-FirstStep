package com.example.demo.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class Form {
    private String name;
    private Integer age;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // 날짜 형식을 yyyy-MM-dd로 받아들이도록 지정
    private LocalDate birth;
}
