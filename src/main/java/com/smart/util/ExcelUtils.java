package com.smart.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.*;

/**
 * excel导出模板，导入数据，导出数据等
 */

@Slf4j
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

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            for (int i = 0; i < fieldNames.size(); i++) {
                writer.setColumnWidth(i, 23);
            }
            writer.flush(baos, true);
            writer.close();
            StaticFileUtils.loadToStream(new ByteArrayInputStream(baos.toByteArray()), request, response, fileName);
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
        } finally {
            // 关闭writer，释放内存
            writer.close();
            //此处记得关闭输出Servlet流
//            IoUtil.close(out);
        }
    }

    public static List<?> importExcel2(MultipartFile file, Class<?> clazz) throws Exception {
        InputStream inputStream = file.getInputStream();
        // 2.应用HUtool ExcelUtil获取ExcelReader指定输入流和sheet
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0);
        // 字段名称集合
        List<String> fieldNames = new ArrayList<>();
        // 字段中文名称集合（获取实体中@ApiModelProperty注解value的值）
        List<String> cnNames = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
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
            }
        }
        String[] fs = fieldNames.toArray(new String[0]);
        String[] ns = cnNames.toArray(new String[0]);
        for (int i = 0; i < ns.length; i++) {
            // 设置表头及字段名
            reader.addHeaderAlias(ns[i], fs[i]);
        }
        List<?> list = reader.readAll(clazz);
        return list;
    }


    public static List<T> importExcel(MultipartFile multipartFile, Class clazz) throws Exception {
        Workbook workbook = null;
        //实例对象
        List<T> list = new ArrayList<>();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String fileName = multipartFile.getOriginalFilename();
            if (fileName.endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                //不是导入模板
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            //不是导入模板
            return null;
        }

        Sheet sheet = workbook.getSheetAt(0);
        //获取总行数
        int rows = sheet.getPhysicalNumberOfRows();
        if (rows < 2) {
            //没有数据
            return null;
        }
        //获取所有字段名
        Field[] fields = clazz.getDeclaredFields();
        //记录对应的中文和英文
        Map<String, String> cMap = new HashMap<>();
        Map<String, Map<String, Class<?>>> countMap = new HashMap<>(fields.length);
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            String fieldName = field.getName();
            // 判断是否有@ApiModelProperty注解
            boolean annotationPresent = field.isAnnotationPresent(ApiModelProperty.class);
            if (annotationPresent && !"deFlag".equals(fieldName) && !"createUser".equals(fieldName)
                    && !"updateUser".equals(fieldName) && !"updateTime".equals(fieldName)
                    && !"createTime".equals(fieldName) && !"businessId".equals(fieldName) && !"monitorId".equals(fieldName)
                    && !"auditId".equals(fieldName) && !"businessId".equals(fieldName) && !"monitorId".equals(fieldName)
                    && !"businessId".equals(fieldName)) {
                ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
                String name = annotation.value();
                Class<?> type = field.getType();
                Map<String, Class<?>> inMap = new HashMap<>();
                inMap.put(fieldName, type);
                countMap.put(name, inMap);
                cMap.put(name, fieldName);
            }

        }
        //获取表头
        Row rowHead = sheet.getRow(0);
        int headNum = rowHead.getPhysicalNumberOfCells();
        //遍历总条数
        for (int c = 1; c < rows; c++) {
            T obj = (T) clazz.newInstance();
            Row rowDate = sheet.getRow(c);
            for (int h = 0; h < headNum; h++) {
                Cell cell = rowDate.getCell(h);
                if (cell != null) {
                    String typeName = rowHead.getCell(h).getStringCellValue();
                    Map<String, Class<?>> inMap = countMap.get(typeName);
                    String typeEName = cMap.get(typeName);
                    String typeztName = inMap.get(typeEName).getName();
                    Method method = obj.getClass().getMethod("set" + initcap(typeEName), inMap.get(typeEName));
                    if ("java.lang.Integer".equals(typeztName) || "int".equals(typeztName)) {
                        Integer param = cell.getColumnIndex();
                        method.invoke(obj, param);
                    } else if ("java.util.Date".equals(typeztName)) {
                        Date param = cell.getDateCellValue();
                        method.invoke(obj, param);
                    } else if ("java.lang.Long".equals(typeztName)) {
                        cell.setCellType(CellType.STRING);
                        Long param = Long.valueOf(cell.getStringCellValue());
                        method.invoke(obj, param);
                    } else if ("java.lang.Double".equals(typeztName)) {
                        cell.setCellType(CellType.STRING);
                        Double param = Double.valueOf(cell.getStringCellValue());
                        method.invoke(obj, param);
                    } else if ("java.time.LocalDateTime".equals(typeztName)) {
                        LocalDateTime param = cell.getLocalDateTimeCellValue();
//                        String pattern = "yyyy-MM-dd HH:mm:ss";
                        method.invoke(obj, param);
                    } else if ("java.lang.String".equals(typeztName)) {
                        cell.setCellType(CellType.STRING);
                        String param = cell.getStringCellValue();
                        method.invoke(obj, param);
                    } else {
                        //不是上述类型
                        log.error("出现未记录的数据类型-----> " + typeztName);
                    }
                }
            }
            //将实体写入集合
            list.add(obj);
        }
        return list;
    }

    private static String initcap(String str) {
        String standardStr = new String();
        if (StrUtil.isNotEmpty(str)) {
            standardStr = str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return standardStr;
    }

}
