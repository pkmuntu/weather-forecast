package com.muntu.exception;

public class WeatherForecastServiceUnavailableException extends  RuntimeException{

    private static final long serialVersionUID = 1L;

    private String field;

    public WeatherForecastServiceUnavailableException(String message) {
        super(message);
    }

    public WeatherForecastServiceUnavailableException(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

