package com.futuremap.mqService.dto;

import lombok.Data;

/**
 * @Author ZuRongTang
 * @Date 2019/11/5
 **/

@Data
public class ContentDto {

    private String headPic;

    private String content;

    private String name;

    private String skipAddress;

    private String skipName;

    private String[] img;

}
