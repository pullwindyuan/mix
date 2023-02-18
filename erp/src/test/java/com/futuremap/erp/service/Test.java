package com.futuremap.erp.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/6/3 17:36
 */
public class Test {
    public static void main(String[] args) {
//        BigDecimal totalAmount = new BigDecimal("0");
//        BigDecimal xx = new BigDecimal("555");
//
//        BigDecimal bigDecimal = new BigDecimal(xx.toString());
//
//        totalAmount = totalAmount.add(bigDecimal);
//        totalAmount = totalAmount.add(bigDecimal);
//        totalAmount = totalAmount.add(bigDecimal);
//        System.out.println(totalAmount);


        boolean matches = new BCryptPasswordEncoder().matches("12345678Qq", "$2a$10$RInM9zv0gpmpXaZnCfrYEOV/iwdg73nrxOhqdu0oFCkID6XMC.Rr6");
        System.out.println(matches);
    }
}
