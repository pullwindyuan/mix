/**
 *
 */
package com.futuremap.custom.controller;
/**
 * @author 作者 E-mail:
 * @version 创建时间：2021年1月27日 上午9:04:15
 * 类说明
 */
//演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
import java.util.Scanner;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import org.apache.commons.lang3.StringUtils;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @author futuremap
 *
 */
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig.Builder("jdbc:mysql://119.23.35.193:35001/custom?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8"
                , "custom", "9Qhb5OaAS6eJYqAV").build();


        // 代码生成器
        AutoGenerator mpg = new AutoGenerator(dsc);

        // 全局配置
        String projectPath = System.getProperty("user.dir");
        GlobalConfig.Builder globalConfigBuilder = new GlobalConfig.Builder();

        globalConfigBuilder.outputDir(projectPath + "/src/main/java")
                .author("futuremap")
                .disableOpenDir();
        GlobalConfig gc = globalConfigBuilder.build();
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.global(gc);

        // 包配置
        PackageConfig.Builder ackageConfigBuilder  = new PackageConfig.Builder("com.futuremap", "模块名");
        PackageConfig pc =ackageConfigBuilder.build();
        mpg.packageInfo(pc);

        // 自定义配置
        InjectionConfig.Builder injectionConfigBuilder = new InjectionConfig.Builder() {};
        injectionConfigBuilder.customFile(null);
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
//     List<FileOutConfig> focList = new ArrayList<>();
//     // 自定义配置会被优先输出
//     focList.add(new (templatePath) {
//         @Override
//         public String outputFile(TableInfo tableInfo) {
//             // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
//             return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
//                     + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
//         }
//     });
     /*
     cfg.setFileCreate(new IFileCreate() {
         @Override
         public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
             // 判断自定义文件夹是否需要创建
             checkDir("调用默认方法创建的目录，自定义目录用");
             if (fileType == FileType.MAPPER) {
                 // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                 return !new File(filePath).exists();
             }
             // 允许生成模板文件
             return true;
         }
     });
     */
        InjectionConfig cfg = injectionConfigBuilder.build();
        mpg.injection(cfg);

        // 配置模板
        TemplateConfig.Builder templateConfigBuilder = new TemplateConfig.Builder();
        templateConfigBuilder.mapperXml(null);
        TemplateConfig templateConfig = templateConfigBuilder.build();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // templateConfig.setService();
        // templateConfig.setController();
        mpg.template(templateConfig);

        // 策略配置
        StrategyConfig.Builder StrategyConfigBuilder = new StrategyConfig.Builder();
        Entity.Builder entityBuilder = StrategyConfigBuilder.entityBuilder();
        entityBuilder.naming(NamingStrategy.underline_to_camel);
        entityBuilder.columnNaming(NamingStrategy.underline_to_camel);
        entityBuilder.superClass("BaseEntity");
        entityBuilder.enableLombok();
        //entityBuilder.setRestControllerStyle(true);
        // 公共父类
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 写于父类中的公共字段
        entityBuilder.addSuperEntityColumns("id");
        //entityBuilder.setInclude(scanner("表名，多个英文逗号分割").split(","));
        Controller.Builder controllerBuilder = StrategyConfigBuilder.controllerBuilder();
        controllerBuilder.enableHyphenStyle();
        //entityBuilder.setTablePrefix(pc.getModuleName() + "_");
        mpg.strategy(StrategyConfigBuilder.build());
        //mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}