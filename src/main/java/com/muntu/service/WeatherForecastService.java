package com.muntu.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.muntu.config.ApplicationPropertyReader;
import com.muntu.dto.WeatherForecastHourlyResponse;
import com.muntu.dto.WeatherForecastSummeryResponse;
import com.muntu.exception.WeatherForecastServiceUnavailableException;
import com.muntu.utils.HTTPUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("WeatherForecastService")
public class WeatherForecastService {

    //NOTE :: we can implement cache for api call to save network call, First we need to analysis data
    private final ApplicationPropertyReader applicationPropertyReader ;

    public WeatherForecastService(ApplicationPropertyReader applicationPropertyReader) {
        this.applicationPropertyReader=applicationPropertyReader;

    }

    public WeatherForecastSummeryResponse getWeatherForecastSummery(String latitude,String longitude) {
        final Map<String, String> headers;
        WeatherForecastSummeryResponse weatherForecastSummeryResponse = null;
        String responseBody = null;
        String url = null;
        try {
            headers = new HashMap<>();
            headers.put("X-RapidAPI-Key", applicationPropertyReader.getProperty("weather_forecast_key"));
            headers.put("X-RapidAPI-Host", applicationPropertyReader.getProperty("weather_forecast_host"));
            headers.put("X-Application-ID",applicationPropertyReader.getProperty("X-Application-ID"));
            url = buildSummeryURl(applicationPropertyReader.getProperty("weather_forecast_baseUrl"), latitude,longitude);
            responseBody = HTTPUtil.connection(url, null, "GET", headers);
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
            weatherForecastSummeryResponse = objectMapper.readValue(responseBody, WeatherForecastSummeryResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new WeatherForecastServiceUnavailableException("WeatherForecast", e.getMessage());
        }
        return weatherForecastSummeryResponse;
    }

    public WeatherForecastHourlyResponse getWeatherForecastHourly(String latitude, String longitude) {

        final Map<String, String> headers;
        WeatherForecastHourlyResponse weatherForecastResponse = null;
        String responseBody = null;
        String url = null;
        try {

            headers = new HashMap<>();
            headers.put("X-RapidAPI-Key", applicationPropertyReader.getProperty("weather_forecast_key"));
            headers.put("X-RapidAPI-Host", applicationPropertyReader.getProperty("weather_forecast_host"));
           // headers.put("X-Application-ID","32857983");
            url = buildHourlyURl(applicationPropertyReader.getProperty("weather_forecast_baseUrl"), latitude,longitude);
            responseBody = HTTPUtil.connection(url, null, "GET", headers);
            ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);;
            weatherForecastResponse = objectMapper.readValue(responseBody, WeatherForecastHourlyResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new WeatherForecastServiceUnavailableException("WeatherForecast", e.toString());
        }
        return weatherForecastResponse;
    }

    private String buildSummeryURl(String baseURL, String latitude,String longitude) {
        return baseURL + latitude+"/"+ longitude + "/summary";
    }

    private String buildHourlyURl(String baseURL, String latitude,String longitude) {
        return baseURL+ latitude+"/"+ longitude +  "/hourly";
    }

}
