 package org.hlpay.base.card.common;

 import org.hlpay.base.card.service.impl.SaCardServiceImpl;
 import org.hlpay.common.util.MyLog;
 import org.springframework.beans.factory.annotation.Autowired;



 public class ScheduledTask
 {
   private static final MyLog _log = MyLog.getLog(ScheduledTask.class);
   @Autowired
   private SaCardServiceImpl saCardService;
 }
