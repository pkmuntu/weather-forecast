package com.muntu.exception.handler;


import com.muntu.api.RestResponse;
import com.muntu.config.ApplicationPropertyReader;
import com.muntu.exception.WeatherForecastServiceUnavailableException;
import com.muntu.utils.RestAPICode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GenericExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = WeatherForecastServiceUnavailableException.class)
    public RestResponse<String> handleTeamNotFoundException(WeatherForecastServiceUnavailableException ex) {
        System.out.println("Inside WeatherForecastServiceUnavailableException Msg {} and Info {} "+ex.getMessage());
        return RestResponse.of(ex.getMessage(), RestAPICode.BAD_REQUEST_STATUS_CODE, LocalDateTime.now(),
                Collections.emptyList());
    }


}
