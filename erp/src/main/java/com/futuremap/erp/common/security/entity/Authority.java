package com.futuremap.erp.common.security.entity;

import lombok.Data;


@Data
public class Authority {

    private Integer id;

    private String name;

//    @JoinColumn(name = "user")
//    @ManyToOne
//    private User user;

}
