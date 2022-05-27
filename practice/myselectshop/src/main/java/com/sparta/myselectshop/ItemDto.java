package com.sparta.myselectshop;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Getter
//@NoArgsConstructor
public class ItemDto {
    // title, link, image, lprice
    private String title;
    private String image;
    private int lprice;
    private String link;

//    public ItemDto(JSONObject itemDto){
//        this.title = itemDto.getString("title");
//        this.image = itemDto.getString("image");
//        this.link = itemDto.getString("link");
//        this.lprice = itemDto.getInt("lprice");
//    }
}
