package com.muntu.controller;

import com.muntu.dto.WeatherForecastSummeryResponse;
import com.muntu.utils.Constants;
import com.muntu.api.RestResponse;
import com.muntu.config.ApplicationPropertyReader;
import com.muntu.dto.WeatherForecastHourlyResponse;
import com.muntu.service.WeatherForecastService;
import com.muntu.utils.RestAPICode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherForecastController {

    private final WeatherForecastService weatherForecastService;
    private final ApplicationPropertyReader propertyReader;

    public WeatherForecastController(final WeatherForecastService weatherForecastService,ApplicationPropertyReader propertyReader){
        this.weatherForecastService=weatherForecastService;
        this.propertyReader=propertyReader;
    }
    @GetMapping("/summery")
    public RestResponse<WeatherForecastSummeryResponse> getWeatherForecastSummery(@RequestParam("latitude") String latitude,@RequestParam(name = "longitude") String longitude){
        return RestResponse.of(weatherForecastService.getWeatherForecastSummery(latitude,longitude), propertyReader.getProperty(Constants.WEATHER_SUMMERY_MESSAGE),
                RestAPICode.OK_STATUS_CODE);
    }

    @GetMapping("/hourly")
    public RestResponse<WeatherForecastHourlyResponse> getWeatherForecastHourly(@RequestParam("latitude") String latitude, @RequestParam("longitude") String longitude){
        return RestResponse.of(weatherForecastService.getWeatherForecastHourly(latitude,longitude), propertyReader.getProperty(Constants.WEATHER_HOURLY_MESSAGE),
                RestAPICode.OK_STATUS_CODE);
    }

}
