package com.futuremap.erp.common.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/9/29 14:48
 */
public class IPageEmpty<T> implements IPage<T> {
    @Override
    public List<OrderItem> orders() {
        return new ArrayList<>();
    }

    @Override
    public List<T> getRecords() {
        return new ArrayList<>();
    }

    @Override
    public IPage<T> setRecords(List<T> records) {
        return null;
    }

    @Override
    public long getTotal() {
        return 0;
    }

    @Override
    public IPage<T> setTotal(long total) {
        return null;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public IPage<T> setSize(long size) {
        return null;
    }

    @Override
    public long getCurrent() {
        return 0;
    }

    @Override
    public IPage<T> setCurrent(long current) {
        return null;
    }
}
