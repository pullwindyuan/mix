/*    */ package org.hlpay.mgr.controller;
/*    */ 
/*    */ import io.swagger.annotations.Api;
/*    */ import java.io.OutputStream;
/*    */ import javax.annotation.Resource;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpServletResponse;
/*    */ import org.hlpay.common.util.FileUtil;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.core.env.Environment;
/*    */ import org.springframework.web.bind.annotation.GetMapping;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ import org.springframework.web.bind.annotation.RestController;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Api(value = "对账单文件下载", tags = {"对账单文件下载"})
/*    */ @RestController
/*    */ @RequestMapping({"/platform/common"})
/*    */ public class CommonController
/*    */ {
/* 26 */   private static final Logger log = LoggerFactory.getLogger(org.hlpay.mgr.controller.CommonController.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Resource
/*    */   private Environment env;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @GetMapping({"/download"})
/*    */   public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request) {
/*    */     try {
/* 40 */       if (!FileUtil.isValidFilename(fileName)) {
/* 41 */         throw new Exception("文件名称非法，不允许下载。 ");
/*    */       }
/* 43 */       String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
/* 44 */       String filePath = this.env.getProperty("uploadPath") + fileName;
/*    */       
/* 46 */       response.setContentType("application/octet-stream");
/* 47 */       FileUtil.setAttachmentResponseHeader(response, realFileName);
/* 48 */       FileUtil.writeBytes(filePath, (OutputStream)response.getOutputStream());
/* 49 */       if (delete.booleanValue()) {
/* 50 */         FileUtil.deleteFile(filePath);
/*    */       }
/* 52 */     } catch (Exception e) {
/* 53 */       log.error("下载文件失败", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\jar\java-concurrency-demo.jar!\BOOT-INF\classes\org\hlpay\mgr\controller\CommonController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */