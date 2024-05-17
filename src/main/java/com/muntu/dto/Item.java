package com.muntu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Item {
    public Date date;
    public int period;
    public double freshSnow;
    public Weather weather;
    public double sunHours;
    public double rainHours;
    public Prec prec;
    public Temperature temperature;
    public double pressure;
    public int relativeHumidity;
    public Clouds clouds;
    public Wind wind;
    public Windchill windchill;
    public SnowLine snowLine;
    public boolean isNight;
}
