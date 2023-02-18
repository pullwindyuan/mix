/*     */ package org.hlpay.mgr.config;
/*     */ 
/*     */ import javax.annotation.Resource;
/*     */ import org.hlpay.base.service.RunModeService;
/*     */ import org.hlpay.common.enumm.RunModeEnum;
/*     */ import org.hlpay.common.util.MyLog;
/*     */ import org.hlpay.mgr.sevice.PlatformService;
/*     */ import org.springframework.beans.BeansException;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.boot.SpringApplication;
/*     */ import org.springframework.boot.context.event.ApplicationReadyEvent;
/*     */ import org.springframework.context.ApplicationContext;
/*     */ import org.springframework.context.ApplicationContextAware;
/*     */ import org.springframework.context.ApplicationEvent;
/*     */ import org.springframework.context.ApplicationListener;
/*     */ import org.springframework.core.env.Environment;
/*     */ import org.springframework.stereotype.Component;
/*     */ 
/*     */ 
/*     */ @Component
/*     */ public class Configuration
/*     */   implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware
/*     */ {
/*  24 */   private static final MyLog _log = MyLog.getLog(org.hlpay.mgr.config.Configuration.class);
/*     */   
/*     */   @Resource
/*     */   private Environment env;
/*     */   
/*     */   @Autowired
/*     */   private PlatformService platformService;
/*     */   @Autowired
/*     */   private RunModeService runModeService;
/*     */   private ApplicationContext applicationContext;
/*     */   
/*     */   public void show() {
/*  36 */     System.out.println(this.applicationContext.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
/*  41 */     this.applicationContext = applicationContext;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onApplicationEvent(ApplicationReadyEvent event) {
/*  46 */     RunModeEnum runMode = this.runModeService.getRunMode();
/*  47 */     String dataBase = this.env.getProperty("deploy.dataBase.schema");
/*     */ 
/*     */     
/*  50 */     if (runMode == null) {
/*  51 */       System.out.println("请先配置服务运行模式后再尝试运行！deploy.runMode=public/private");
/*     */       try {
/*  53 */         throw new Exception("请先配置服务运行模式后再尝试运行! deploy.runMode=public/private");
/*  54 */       } catch (Exception e) {
/*  55 */         e.printStackTrace();
/*  56 */         SpringApplication.exit(this.applicationContext, new org.springframework.boot.ExitCodeGenerator[0]);
/*     */         return;
/*     */       } 
/*     */     } 
/*  60 */     if (runMode.name().equals(RunModeEnum.PRIVATE.name())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 122 */       System.out.println("当前服务运行在私域模式！");
/* 123 */       _log.info("当前服务运行在私域模式！", new Object[0]);
/*     */       
/*     */       try {
/* 126 */         this.platformService.createDefaultPlatform();
/* 127 */       } catch (Exception e) {
/* 128 */         e.printStackTrace();
/* 129 */         SpringApplication.exit(this.applicationContext, new org.springframework.boot.ExitCodeGenerator[0]);
/*     */       } 
/* 131 */     } else if (runMode.name().equals(RunModeEnum.PUBLIC.name())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 144 */       System.out.println("当前服务运行在公域平台模式！");
/* 145 */       _log.info("当前服务运行在公域平台模式！", new Object[0]);
/* 146 */     } else if (runMode.name().equals(RunModeEnum.PRIVATE_INDEPENDENT.name())) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 159 */       System.out.println("当前服务运行在私域独立收款模式！");
/* 160 */       _log.info("当前服务运行在私域独立收款模式！", new Object[0]);
/*     */     } else {
/* 162 */       System.out.println("请先配置服务运行模式payment.runMode = public or private / private_independent！");
/*     */       try {
/* 164 */         throw new Exception("请先配置服务运行模式payment.runMode = public or private / private_independent！");
/* 165 */       } catch (Exception e) {
/* 166 */         e.printStackTrace();
/* 167 */         SpringApplication.exit(this.applicationContext, new org.springframework.boot.ExitCodeGenerator[0]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\jar\java-concurrency-demo.jar!\BOOT-INF\classes\org\hlpay\mgr\config\Configuration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */