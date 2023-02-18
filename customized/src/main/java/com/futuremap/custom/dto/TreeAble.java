package com.futuremap.custom.dto;

import java.util.List;

public interface TreeAble<T extends TreeAble> {

    String getId();

    String getParentId();

    void setChildren(List<T> children);
    
    List<T> getChildren();

    
}