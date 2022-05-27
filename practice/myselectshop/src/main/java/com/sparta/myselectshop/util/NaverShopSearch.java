package com.sparta.myselectshop.util;

import com.sparta.myselectshop.dto.ItemDto;
import com.sparta.myselectshop.dto.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component // Component 권한 획득
public class NaverShopSearch {
    // Controller 가 자동으로 해주는 일
    // 1. API Request 의 파라미터 값에서 검색어 추출 -> query 변수
    // 5. API Response 보내기
    //  5.1) response 의 header 설정
    //  5.2) response 의 body 설정
    public String search(String query) {
        // 2. 네이버 쇼핑 API 호출에 필요한 Header, Body 정리
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "sEBnM8tt4QATAakm409G");
        headers.add("X-Naver-Client-Secret", "WllcskSG2Q");
        String body = "";
        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        // 2. 네이버 쇼핑 API 호출에 필요한 Header, Body 정리
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query=" + query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = responseEntity.getStatusCode();
        int status = httpStatus.value();
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);
        System.out.println(response);

        return response;
    }

    // 4. naverApiResponseJson (JSON 형태) -> itemDtoList (자바 객체 형태)
    //  - naverApiResponseJson 에서 우리가 사용할 데이터만 추출 -> List<ItemDto> 객체로 변환
    public List<ItemDto> fromJSONItem(String result){
        JSONObject rjson = new JSONObject(result);
        JSONArray items = rjson.getJSONArray("items");

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 0; i < items.length(); i++) {
            JSONObject itemJson = items.getJSONObject(i);
            ItemDto itemDto = new ItemDto(itemJson);
            itemDtoList.add(itemDto);
        }
        return itemDtoList;
    }
}