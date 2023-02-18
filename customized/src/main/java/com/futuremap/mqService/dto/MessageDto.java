package com.futuremap.mqService.dto;

import lombok.Data;

/**
 * @Author ZuRongTang
 * @Date 2019/11/5
 **/

@Data
public class MessageDto {

    private String appId;

    private String token;

    private String fromId;

    private String fromName;

    private String fromPlatformName;

    private String toId;

    private String toName;

    private String toPlatformName;

    private String content;

    private Integer contentType;

    private String isGroupNotice;

    private Integer msgClass;

    private String isNotice;

    private String isSynchronization;

}
