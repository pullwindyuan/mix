 package org.hlpay.boot.config


 import com.github.pagehelper.StringUtil;
 import javax.annotation.Resource;
 import org.hlpay.base.service.RunModeService;
 import org.hlpay.common.enumm.RunModeEnum;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.BeansException;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.SpringApplication;
 import org.springframework.boot.context.event.ApplicationReadyEvent;
 import org.springframework.context.ApplicationContext;
 import org.springframework.context.ApplicationContextAware;
 import org.springframework.context.ApplicationEvent;
 import org.springframework.context.ApplicationListener;
 import org.springframework.core.env.Environment;
 import org.springframework.stereotype.Component;


 @Component
 public class Configuration
   implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware
 {
   private static final MyLog _log = MyLog.getLog(org.hlpay.boot.config.Configuration.class);


   @Resource
   private Environment env;


   @Autowired
   private RunModeService runModeService;

   private ApplicationContext applicationContext;


   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
     this.applicationContext = applicationContext;
   }


   public void onApplicationEvent(ApplicationReadyEvent event) {
     RunModeEnum runMode = this.runModeService.getRunMode();
     String platformId = this.env.getProperty("deploy.publicPlatformPayMgrDeployUrl");
     String dataBase = this.env.getProperty("deploy.database.schema");
     if (runMode == null) {
       System.out.println("请先配置服务运行模式后再尝试运行！");
       try {
         throw new Exception("请先配置服务运行模式后再尝试运行");
       } catch (Exception e) {
         e.printStackTrace();
         SpringApplication.exit(this.applicationContext, new org.springframework.boot.ExitCodeGenerator[0]);
         return;
       }
     }
     if (runMode.name().equals(RunModeEnum.PRIVATE.name())) {












       if (StringUtil.isEmpty(platformId)) {
         System.out.println("请先配置平台支付中心pay-service服务对应部署地址（publicPlatformPayMgrDeployUrl）后再尝试运行！");
         try {
           throw new Exception("请先配置平台支付中心pay-service服务对应部署地址（publicPlatformPayMgrDeployUrl）后再尝试运行！");
         } catch (Exception e) {
           e.printStackTrace();
           SpringApplication.exit(this.applicationContext, new org.springframework.boot.ExitCodeGenerator[0]);
         }
       } else {
         System.out.println("当前服务运行在私域模式！");
         _log.info("当前服务运行在私域模式！", new Object[0]);
       }
     } else if (runMode.name().equals(RunModeEnum.PUBLIC.name())) {












       System.out.println("当前服务运行在公域平台模式！");
       _log.info("当前服务运行在公域平台模式！", new Object[0]);
     } else if (runMode.name().equals(RunModeEnum.PRIVATE_INDEPENDENT.name())) {












       System.out.println("当前服务运行在私域独立收款模式！");
       _log.info("当前服务运行在私域独立收款模式！", new Object[0]);
     } else {
       System.out.println("请先配置服务运行模式payment.runMode = public or private / private_independent！");
       try {
         throw new Exception("请先配置服务运行模式payment.runMode = public or private / private_independent！");
       } catch (Exception e) {
         e.printStackTrace();
         SpringApplication.exit(this.applicationContext, new org.springframework.boot.ExitCodeGenerator[0]);
         return;
       }
     }
   }
 }





