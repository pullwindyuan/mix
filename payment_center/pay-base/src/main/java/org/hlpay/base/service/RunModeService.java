 package org.hlpay.base.service;

 import javax.annotation.Resource;
 import org.hlpay.common.enumm.RunModeEnum;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Service;

 @Service
 public class RunModeService
 {
   @Resource
   private Environment env;
   private static RunModeEnum runMode;

   public RunModeEnum getRunMode() {
     if (runMode != null) {
       return runMode;
     }
     runMode = RunModeEnum.valueOf(this.env.getProperty("deploy.runMode").toUpperCase());
     return runMode;
   }

   public int getRunModeCode() {
     return getRunMode().getCode().intValue();
   }
 }
