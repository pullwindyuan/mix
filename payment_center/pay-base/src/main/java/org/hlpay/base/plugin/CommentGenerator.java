 package org.hlpay.base.plugin;

 import org.mybatis.generator.api.IntrospectedColumn;
 import org.mybatis.generator.api.IntrospectedTable;
 import org.mybatis.generator.api.dom.java.Field;
 import org.mybatis.generator.api.dom.java.JavaElement;
 import org.mybatis.generator.internal.DefaultCommentGenerator;







 public class CommentGenerator
   extends DefaultCommentGenerator
 {
   public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
     super.addFieldComment(field, introspectedTable, introspectedColumn);
     if (introspectedColumn.getRemarks() != null && !introspectedColumn.getRemarks().equals("")) {
       field.addJavaDocLine("/**");
       field.addJavaDocLine(" * " + introspectedColumn.getRemarks());
       addJavadocTag((JavaElement)field, false);
       field.addJavaDocLine(" */");
     }
   }
 }

