package com.example.login_test.weather;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter @Setter @ToString
public class WeatherResponse {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private Integer visibility;
    private Wind wind;
    private Clouds clouds;
    private Long dt;
    private Sys sys;
    private Integer timezone;
    private Long id;
    private String name;
    private Integer cod;


    @Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Coord {
        private Double lon;
        private Double lat;
    }


    @Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Weather {
        private Integer id;
        private String main;
        private String description;
        private String icon;
    }


    @Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Main {
        private Double temp;

        @JsonProperty("feels_like")
        private Double feelsLike;

        @JsonProperty("temp_min")
        private Double tempMin;

        @JsonProperty("temp_max")
        private Double tempMax;

        private Integer pressure;

        private Integer humidity;
    }


    @Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Wind {
        private Double speed;
        private Integer deg;
    }

    @Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Clouds {
        private Integer all;
    }


    @Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
    public static class Sys {
        private Integer type;
        private Long id;
        private String country;
        private Long sunrise;
        private Long sunset;
    }


}
