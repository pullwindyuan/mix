/*
 * Copyright © 2017-2021 http://www.futuremap.com.cn/ All rights reserved.
 *
 * Statement: This document's code after sufficiently has not permitted does not have
 * any way dissemination and the change, once discovered violates the stipulation, will
 * receive the criminal sanction.
 * Address: Building A, block 1F,  Tian'an Yungu Industrial Park phase I,
 *          Xuegang Road, Bantian street, Longgang District, Shenzhen
 * Tel: 0755-22674916
 */
package com.futuremap.erp.utils;

import java.util.EnumSet;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Administrator
 * @title CurrencyUtil
 * @description TODO
 * @date 2021/6/4 15:15
 */
public class CurrencyUtil {

    public static String convertCode(String currency){
        EnumSet<Currency> currencies = EnumSet.allOf(Currency.class);
        AtomicReference<String> code = new AtomicReference<>("");
        if(currency.matches("[a-zA-Z]+")){
            currencies.stream().forEach(e->{
                if(currency.equals(e.getCode())){
                    code.set(currency);

                }
            });
        }else {
            currencies.stream().forEach(e->{
                if(currency.equals(e.getName())){
                   code.set(e.getCode());
                }
            });
        }

        return code.get();
    }
    public static String convertName(String currency){
        EnumSet<Currency> currencies = EnumSet.allOf(Currency.class);
        AtomicReference<String> code = new AtomicReference<>("");
        if(currency.matches("[a-zA-Z]+")){
            currencies.stream().forEach(e->{
                if(currency.equals(e.getCode())){
                   code.set(e.getName());
                }
            });
        }else {
            currencies.stream().forEach(e->{
                if(currency.equals(e.getName())){
                    code.set(currency);
                }
            });
        }

        return code.get();
    }

    enum Currency{

        RMB("人民币","RMB"),USD("美元","USD"),EUR("欧元","EUR"),AUD("欧元","AUD"),HKD("港元","HKD"),CAD("加元","CAD"),GBP("英镑","GBP"),JPY("日元","JPY");
        private String name;
        private String code;

        private Currency(String name, String code) {
            this.name = name;
            this.code = code;
        }
        public String getName(){
            return this.name;
        }
        public String getCode(){
            return this.code;
        }

    }


    public static void main(String[] args) {
        String code = "美元";
        String s = CurrencyUtil.convertCode(code);
        System.out.println(s);
    }

}
