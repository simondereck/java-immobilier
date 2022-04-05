package com.utudo.hwwd.helpers;

import com.utudo.hwwd.models.extModel.MapBoxGeocodingResponse;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class HwHttpClient {


    private static String url = "https://api.mapbox.com/geocoding/v5/mapbox.places/";

    private static String token = ".json?access_token=pk.eyJ1IjoiaGFpd2FpaHVhbmdkaSIsImEiOiJja2huZGpmZmUyZW5pMnJxcXA0bHlycmc0In0.Wh2Kzu-CfQYA0D91x5YyNg";

    public static MapBoxGeocodingResponse sendGetReqeust(String params){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url+params+token , MapBoxGeocodingResponse.class,new HashMap<>());
    }

    public static Object MapBoxAutoCompleteAddress(){
        String url = "https://api.mapbox.com/geocoding/v5/mapbox.places-permanent/fairbanks,alaska;aslkdjf;juno,alaska"+token+"&limit=1";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url , Object.class,new HashMap<>());
    }
}
