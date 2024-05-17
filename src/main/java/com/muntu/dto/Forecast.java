package com.muntu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Forecast {
    public ArrayList<Item> items;
    public Date forecastDate;
    public Date nextUpdate;
    public String source;
    public String point;
}
