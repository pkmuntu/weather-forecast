package com.muntu.dto;

import java.util.ArrayList;
import java.util.Date;

public class WeatherForecastSummeryResponse {
    public ArrayList<Item> items;
    public Date forecastDate;
    public Date nextUpdate;
    public String source;
    public String point;
    public String fingerprint;
}
