package com.smart.util;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import io.swagger.annotations.ApiModelProperty;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * excel导出模板，导入数据，导出数据等
 */
public class ExcelUtils {

    public static void createExcel(HttpServletRequest request, HttpServletResponse response, Class clazz, List<?> list, String fileName) {

        // 创建ExcelWrite对象
        // 生成xls文件
        // flase HSSFWorkbook xls 2003
        // true XSSFWorkbook  xlsx 2007
        ExcelWriter writer = ExcelUtil.getWriter();

        // 获取当前类字段
        Field[] fields = clazz.getDeclaredFields();

        // 字段名称集合
        List<String> fieldNames = new ArrayList<>();
        // 字段中文名称集合（获取实体中@ApiModelProperty注解value的值）

        List<String> cnNames = new ArrayList<>();
//        .xls      application/vnd.ms-excel
//        .doc      application/msword
        String mimeType = request.getServletContext().getMimeType(fileName);
        if (mimeType != null && !"".equals(mimeType)) {
            response.setContentType(mimeType);
        }
        for (Field field : fields) {
            if (!field.isAccessible()) {
                // 关闭反射访问安全检查，为了提高速度
                field.setAccessible(true);
            }
            // 获取字段名称
            String fieldName = field.getName();
            // 判断哪些字段不需要输出
            /*if (!"deFlag".equals(fieldName) && !"serialVersionUID".equals(fieldName) && !"createUser".equals(fieldName)
                    && !"updateUser".equals(fieldName) && !"updateTime".equals(fieldName)) {
                fieldNames.add(fieldName);
            }*/
            // 判断是否有@ApiModelProperty注解
            boolean annotationPresent = field.isAnnotationPresent(ApiModelProperty.class);
            if (annotationPresent && !"deFlag".equals(fieldName) && !"createUser".equals(fieldName)
                    && !"updateUser".equals(fieldName) && !"updateTime".equals(fieldName)) {
                fieldNames.add(fieldName);
                ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                String name = annotation.value();
                cnNames.add(name);
            } else {
                //排除字段操作(如果为true，则不设置alias的字段将不被输出)
                writer.setOnlyAlias(true);
            }
        }
        String[] fs = fieldNames.toArray(new String[0]);
        String[] ns = cnNames.toArray(new String[0]);
        for (int i = 0; i < ns.length; i++) {
            // 设置表头及字段名
            writer.addHeaderAlias(fs[i], ns[i]);
        }
        // yzj
        // 自动换行
        Workbook workbook = writer.getWorkbook();
        StyleSet styleSet = new StyleSet(workbook);
        styleSet.setWrapText();
        writer.setStyleSet(styleSet);
        writer.write(list, true);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            for(int i = 0; i < fieldNames.size(); i++) {
                writer.setColumnWidth(i, 23);
            }
            writer.flush(baos, true);
            writer.close();
            StaticFileUtils.loadToStream(new ByteArrayInputStream(baos.toByteArray()),request,response,fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public static void createExcel2(HttpServletRequest request, HttpServletResponse response, Class clazz, List<?> list, String fileName) {
        // 创建ExcelWrite对象
        // 生成xls文件
        // flase HSSFWorkbook xls 2003
        // true XSSFWorkbook  xlsx 2007
        ExcelWriter writer = ExcelUtil.getWriter();

        // 获取当前类字段
        Field[] fields = clazz.getDeclaredFields();

        // 字段名称集合
        List<String> fieldNames = new ArrayList<>();
        // 字段中文名称集合（获取实体中@ApiModelProperty注解value的值）

        List<String> cnNames = new ArrayList<>();
//        .xls      application/vnd.ms-excel
//        .doc      application/msword
        String mimeType = request.getServletContext().getMimeType(fileName);
        if (mimeType != null && !"".equals(mimeType)) {
            response.setContentType(mimeType);
        }
        for (Field field : fields) {
            if (!field.isAccessible()) {
                // 关闭反射访问安全检查，为了提高速度
                field.setAccessible(true);
            }
            // 获取字段名称
            String fieldName = field.getName();
            // 判断哪些字段不需要输出
            // 判断是否有@ApiModelProperty注解
            boolean annotationPresent = field.isAnnotationPresent(ApiModelProperty.class);
            if (annotationPresent && !"deFlag".equals(fieldName) && !"createUser".equals(fieldName)
                    && !"updateUser".equals(fieldName) && !"updateTime".equals(fieldName)) {
                fieldNames.add(fieldName);
                ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                String name = annotation.value();
                cnNames.add(name);
            } else {
                //排除字段操作(如果为true，则不设置alias的字段将不被输出)
                writer.setOnlyAlias(true);
            }
        }
        String[] fs = fieldNames.toArray(new String[0]);
        String[] ns = cnNames.toArray(new String[0]);
        for (int i = 0; i < ns.length; i++) {
            // 设置表头及字段名
            writer.addHeaderAlias(fs[i], ns[i]);
        }

        // 自动换行
        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);
        // 列自适应宽
        writer.autoSizeColumnAll();
        //out为OutputStream，需要写出到的目标流
        //response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
            try {
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        try (ServletOutputStream out = response.getOutputStream()) {
            writer.flush(out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            // 关闭writer，释放内存
            writer.close();
            //此处记得关闭输出Servlet流
//            IoUtil.close(out);
        }
    }


}
