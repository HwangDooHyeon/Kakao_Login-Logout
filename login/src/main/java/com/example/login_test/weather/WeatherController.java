package com.example.login_test.weather;


import com.example.login_test.core.utils.ApiUtils;
import com.example.login_test.core.utils.SignUpMessageSender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Optional;

@RestController
public class WeatherController {

    @PostMapping("/weather")
    public ResponseEntity<?> getWeather() throws JsonProcessingException {

        String city = "Busan";
        String apikey = "b7c489cbd6b790f63e1888d163431f43";
        String phoneNumber = "01029055534";
        String units = "metric"; // 섭시로 설정 => 화시: imperial
        String weatherApiUrl = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=%s", city, apikey, units);

        RestTemplate restTemplate = new RestTemplate();
        WeatherResponse weatherResponse = restTemplate.getForObject(weatherApiUrl, WeatherResponse.class);

        Optional<WeatherResponse.Weather> optionalWeather = weatherResponse.getWeather().stream()
                .filter(w -> "Mist".equals(w.getMain())) // 필터링 조건을 여기에 추가
                .findFirst();

        String weatherResult = "온도 : " + weatherResponse.getMain().getTemp();

        if (optionalWeather.isPresent()) {
            weatherResult = optionalWeather.get().getDescription() + weatherResult;
        }

        System.out.println(weatherResult);
        SignUpMessageSender.sendMessage(phoneNumber, phoneNumber, weatherResult);

        return ResponseEntity.ok( ApiUtils.success(null) );
    }

}



/*
{
    "coord": {
        "lon": 126.9778, // 경도
        "lat": 37.5683  // 위도
    },

    "weather": [
        {
            "id": 701, // 기상 조건 ID
            "main": "Mist", // 날씨 매개변수 그룹(비, 눈, 구름 ​​등)
            "description": "mist", // 그룹 내 날씨 상태. 자세한 내용은 여기를 참조하세요 . 귀하의 언어로 출력을 얻을 수 있습니다. 더 알아보기
            "icon": "50d" // 날씨 아이콘 ID
        }
    ],
    "base": "stations",
    "main": {
        "temp": 292.44, // 온도. 단위 기본값: 켈빈, 미터법: 섭씨, 영국식: 화씨
        "feels_like": 292.33, // 온도. 이 온도 매개변수는 날씨에 대한 인간의 인식을 설명합니다. 단위 기본값: 켈빈, 미터법: 섭씨, 영국식: 화씨
        "temp_min": 290.81, // hPa 현재 최저기온입니다. 이는 현재 관측된 최소 온도입니다(대규모 대도시 및 도시 지역 내). 자세한 내용은 여기에서 확인하세요 . 단위 기본값: 켈빈, 미터법: 섭씨, 영국식: 화씨
        "temp_max": 292.91, // 현재 최고온도. 이는 현재 관찰된 최대 온도입니다(대규모 대도시 및 도시 지역 내). 자세한 내용은 여기에서 확인하세요 . 단위 기본값: 켈빈, 미터법: 섭씨, 영국식: 화씨
        "pressure": 1016, // 해수면의 대기압
        "humidity": 73 // 습도, %
    },
    "visibility": 4500, // 가시성, 미터. 가시성의 최대 값은 10km입니다
    "wind": {
        "speed": 4.12, // 바람 속도. 단위 기본값: 미터/초, 미터법: 미터/초, 영국식: 마일/시간
        "deg": 210 // 각도(기상)
    },
    "clouds": {
        "all": 75 // 흐림, %
    },
    "dt": 1698286992,

    // rain.1h (가능한 경우) 지난 1시간 동안의 강우량(mm). 이 매개변수에는 측정 단위로 mm만 사용할 수 있습니다.
    // rain.3h (가능한 경우) 지난 3시간 동안의 강우량(mm). 이 매개변수에는 측정 단위로 mm만 사용할 수 있습니다.
    // dt데이터 계산 시간, unix, UTC
    "sys": {
        "type": 1, // 내부 매개변수
        "id": 8105, // 내부 매개변수
        // sys.message 내부 매개변수
        "country": "KR", // 국가 코드
        "sunrise": 1698270600, // 일출 시간, 유닉스, UTC
        "sunset": 1698309721 // 일몰 시간, 유닉스, UTC
    },
    "timezone": 32400, // UTC에서 초 단위로 이동
    "id": 1835848, // 도시 ID.
    "name": "Seoul", // 도시 이름
    "cod": 200 // 내부 매개변수
}
 */