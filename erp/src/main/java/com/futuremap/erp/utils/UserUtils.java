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

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

/**
 * @author Administrator
 * @title UserUtils
 * @description TODO
 * @date 2021/8/5 11:52
 */
public class UserUtils {

   public static boolean hasRole(String needRole){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (!(authentication instanceof AnonymousAuthenticationToken)){
           Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
           for (GrantedAuthority authority : authorities) {
               String roleName = authority.getAuthority();
               if (roleName.equals(needRole)) {
                   return true;
               }
           }
       }
       return  false;
   }

   public static String getCurrentUserName(){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (!(authentication instanceof AnonymousAuthenticationToken)) {
           String currentUserPhone = authentication.getName();
               return  currentUserPhone;
           }
       return null;
   }
}
