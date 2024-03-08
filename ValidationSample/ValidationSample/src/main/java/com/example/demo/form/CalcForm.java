package com.example.demo.form;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

@Data
public class CalcForm {
    @NotNull
    @Range(min = 1, max = 10)
    private Integer leftNum;

    @NotNull
    @Range(min = 1, max = 10)
    private Integer rightNum;
}
