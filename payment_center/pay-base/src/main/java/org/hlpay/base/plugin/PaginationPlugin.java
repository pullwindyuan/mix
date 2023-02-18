 package org.hlpay.base.plugin;

 import java.util.List;
 import org.mybatis.generator.api.IntrospectedTable;
 import org.mybatis.generator.api.PluginAdapter;
 import org.mybatis.generator.api.dom.java.Field;
 import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
 import org.mybatis.generator.api.dom.java.JavaVisibility;
 import org.mybatis.generator.api.dom.java.Method;
 import org.mybatis.generator.api.dom.java.Parameter;
 import org.mybatis.generator.api.dom.java.PrimitiveTypeWrapper;
 import org.mybatis.generator.api.dom.java.TopLevelClass;
 import org.mybatis.generator.api.dom.xml.Attribute;
 import org.mybatis.generator.api.dom.xml.Element;
 import org.mybatis.generator.api.dom.xml.TextElement;
 import org.mybatis.generator.api.dom.xml.XmlElement;


 public class PaginationPlugin
   extends PluginAdapter
 {
   public boolean validate(List<String> list) {
     return true;
   }






   public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
     PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

     Field limit = new Field();
     limit.setName("limit");
     limit.setVisibility(JavaVisibility.PRIVATE);
     limit.setType((FullyQualifiedJavaType)integerWrapper);
     topLevelClass.addField(limit);

     Method setLimit = new Method();
     setLimit.setVisibility(JavaVisibility.PUBLIC);
     setLimit.setName("setLimit");
     setLimit.addParameter(new Parameter((FullyQualifiedJavaType)integerWrapper, "limit"));
     setLimit.addBodyLine("this.limit = limit;");
     topLevelClass.addMethod(setLimit);

     Method getLimit = new Method();
     getLimit.setVisibility(JavaVisibility.PUBLIC);
     getLimit.setReturnType((FullyQualifiedJavaType)integerWrapper);
     getLimit.setName("getLimit");
     getLimit.addBodyLine("return limit;");
     topLevelClass.addMethod(getLimit);

     Field offset = new Field();
     offset.setName("offset");
     offset.setVisibility(JavaVisibility.PRIVATE);
     offset.setType((FullyQualifiedJavaType)integerWrapper);
     topLevelClass.addField(offset);

     Method setOffset = new Method();
     setOffset.setVisibility(JavaVisibility.PUBLIC);
     setOffset.setName("setOffset");
     setOffset.addParameter(new Parameter((FullyQualifiedJavaType)integerWrapper, "offset"));
     setOffset.addBodyLine("this.offset = offset;");
     topLevelClass.addMethod(setOffset);

     Method getOffset = new Method();
     getOffset.setVisibility(JavaVisibility.PUBLIC);
     getOffset.setReturnType((FullyQualifiedJavaType)integerWrapper);
     getOffset.setName("getOffset");
     getOffset.addBodyLine("return offset;");
     topLevelClass.addMethod(getOffset);

     return true;
   }







   public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
     XmlElement ifLimitNotNullElement = new XmlElement("if");
     ifLimitNotNullElement.addAttribute(new Attribute("test", "limit != null"));

     XmlElement ifOffsetNotNullElement = new XmlElement("if");
     ifOffsetNotNullElement.addAttribute(new Attribute("test", "offset != null"));
     ifOffsetNotNullElement.addElement((Element)new TextElement("limit ${offset}, ${limit}"));
     ifLimitNotNullElement.addElement((Element)ifOffsetNotNullElement);

     XmlElement ifOffsetNullElement = new XmlElement("if");
     ifOffsetNullElement.addAttribute(new Attribute("test", "offset == null"));
     ifOffsetNullElement.addElement((Element)new TextElement("limit ${limit}"));
     ifLimitNotNullElement.addElement((Element)ifOffsetNullElement);

     element.addElement((Element)ifLimitNotNullElement);

     return true;
   }
 }

