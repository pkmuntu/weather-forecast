package com.muntu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@SpringBootApplication
public class WeatherForecastApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(WeatherForecastApplication.class, args);
		/*HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://forecast9.p.rapidapi.com/rapidapi/forecast/Berlin/summary/"))
				.header("X-RapidAPI-Key", "ed60b61e1amsh35948ab90e94368p1ad876jsna77d9e0ff5f8")
				.header("X-RapidAPI-Host", "forecast9.p.rapidapi.com")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());*/
	}

}
