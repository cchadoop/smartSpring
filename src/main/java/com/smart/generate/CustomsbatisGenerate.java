package com.smart.generate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * mybatis plus代码生成器
 */
public class CustomsbatisGenerate {

    private static String author = "smart";

    private static String out_put_dir = "D:\\study\\jwtToken\\src\\main\\java";
    //包名，导入使用

    // 数据库配置
    private static String username = "PS_BONDED_CUSTOMS";
    private static String password = "123456";
    private static String url = "jdbc:oracle:thin:@127.0.0.1:1521/ORCL";
    private static DbType db_type = DbType.ORACLE;
    private static String driverClassName = "oracle.jdbc.driver.OracleDriver";


    private static String[] module = {
//            "SYS_MENU",
//            "SYS_USER"
            "SYS_ROLE"
    };

    private static String[] entity_ignore_prefix = {
            ""
    };

    //包名，导入使用
    private static String package_path = "/com/smart";

    //文件输出路径
    private static String project_path = System.getProperty("user.dir");

    private static String java_path = project_path + "/src/main/java" + package_path;
    private static String entity_path = java_path + "/entity";
    private static String mapper_path = java_path + "/mapper";
    private static String service_path = java_path + "/service";
    private static String service_impl_path = java_path + "/service/impl";
    private static String controller_path = java_path + "/controller";
    private static String resource_path = project_path + "/src/main";
    private static String xml_path = resource_path + "/resources/mapper";
//    private static String xml_path = resource_path + "/resources/mybatis-mapper" + package_path + "/mapper";


    //文件输出模板
    private static String entity_template = "templates/freemarker/entity.java.ftl";
    private static String xml_template = "templates/freemarker/mapper.xml.ftl";
    private static String mapper_template = "templates/freemarker/mapper.java.ftl";
    private static String service_template = "templates/freemarker/service.java.ftl";
    private static String service_impl_template = "templates/freemarker/serviceImpl.java.ftl";
    private static String controller_template = "templates/freemarker/base/controller.java.ftl";

    private static String controller_template_path = "templates/freemarker/base/controller.java.ftl";


    public static void main(String[] args) {
        String ftl = controller_template.substring(0, controller_template.indexOf("ftl")-1);
        // 配置模板
        FastAutoGenerator.create(url, username, password)
                .templateConfig(builder -> {
                    builder.controller(ftl);
                })
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
//                            .disableOpenDir() // 生成文件后打开目录
                            .outputDir(out_put_dir); // 指定输出目录
                })
                .packageConfig(builder -> {
                    String parent = package_path.replace('/', '.').substring(1);
                    builder.parent(parent) // 设置父包名
                            .moduleName("") // 设置父包模块名
//                            .entity(entity_path)
//                            .mapper(mapper_path)
//                            .service(service_path)
//                            .serviceImpl(service_impl_path)
//                            .controller(controller_path)
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, xml_path)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(module) // 设置需要生成的表名
                            .addTablePrefix(entity_ignore_prefix)// 设置过滤表前缀
                            .controllerBuilder().enableRestStyle().enableHyphenStyle()
                            .serviceBuilder().formatServiceFileName("%sService")
                            .mapperBuilder().enableBaseColumnList().enableBaseResultMap().enableMapperAnnotation()
                            .entityBuilder().enableTableFieldAnnotation().enableLombok();
                })
                /*.injectionConfig(builder -> {
                    builder.beforeOutputFile((tableInfo, stringObjectMap)->{
                        String s = controller_path + File.separator + tableInfo.getControllerName() + StringPool.DOT_JAVA;
                        System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + stringObjectMap.size());
                    }).customFile(Collections.singletonMap("controller.java", controller_template));
                })*/
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

    private static InjectionConfig injectionConfig(){
        /**
         * 自定义配置
         */
        InjectionConfig build = new InjectionConfig.Builder().beforeOutputFile(((tableInfo, stringObjectMap) -> {
                    System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + stringObjectMap.size());
                }))
                .customFile(Collections.singletonMap("controller.java", controller_template))
        .customFile(Collections.singletonMap("mapper.xml", xml_template))
        .customFile(Collections.singletonMap("bean.java", entity_template))
        .customFile(Collections.singletonMap("service.java", service_template))
        .customFile(Collections.singletonMap("serviceImpl.java", service_impl_template))
        .customFile(Collections.singletonMap("mapper.java", mapper_template))
                .build();

        return build;
    }




}