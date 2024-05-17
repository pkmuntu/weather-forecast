package com.muntu.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Wind {
    public String unit;
    public String direction;
    public int avg;
    public int min;
    public int max;
    public String text;
    public boolean significationWind;
    public Gusts gusts;
}
