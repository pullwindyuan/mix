package com.futuremap.base.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Page  implements Serializable {
    private Integer page;
    private Integer size;
    private Long count;
    private List  list;
}
