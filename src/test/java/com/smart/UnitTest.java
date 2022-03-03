package com.smart;

import cn.hutool.core.lang.Pair;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONArray;
import com.smart.entity.SysMenu;
import com.smart.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class UnitTest {


    /**
     * 读取静态资源
     */
    @Test
    public void testResource() {
        String jsonStr = "";
        try {
            String path = this.getClass().getClassLoader().getResource("json/sys_user.json").getPath();
            File file = new File(path);
            FileReader fileReader = new FileReader(file);
            Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
            int ch = 0 ;
            StringBuffer sb = new StringBuffer();
            while ( (ch=reader.read())!=-1){
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        List<SysUser> list = JSONArray.parseArray(jsonStr, SysUser.class);
        System.out.println(list);
    }

    @Test
    public void testPair() {
        Pair<Integer, String> pair = new Pair<>(1, "One");
        Integer key = pair.getKey();
        String value = pair.getValue();
        System.out.println(key + "------" + value);

        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(1);

        System.out.println(threadLocal.get());

    }

    /**
     * 测试hutool excel导入
     */
    @Test
    public void testExcel(){
        ExcelReader reader = ExcelUtil.getReader("C:\\Users\\13182\\Desktop\\菜单.xls");
        // 字段名称集合
        List<String> fieldNames = new ArrayList<>();
        // 字段中文名称集合（获取实体中@ApiModelProperty注解value的值）
        List<String> cnNames = new ArrayList<>();
        Field[] fields = SysMenu.class.getDeclaredFields();
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
            // 设置表头及字段名  与导出时相反
            reader.addHeaderAlias(ns[i], fs[i]);
        }
        List<SysMenu> all = reader.readAll(SysMenu.class);
        System.out.println(all);
    }


    @Test
    public void test() {
        jj:
        for (int j = 0; j < 2; j++) {
            ii:
            for (int i =0 ; i < 6; i++) {
                if (i > 3) {
                    break jj;
                }
                System.out.println("----III的值：" + i);  // 0 , 1 ,2 ,3
            }
            System.out.println("----JJJ的值：" + j); // 1 2 1 2
        }
    }

    @Test
    public void test2() {
        for (int j = 0; j < 2; j++) {
            for (int i =0 ; i < 6; i++) {
                if (i > 3) {
                    break;
                }
                System.out.println("----III的值：" + i);  // 0 , 1 ,2 ,3
            }
            System.out.println("----JJJ的值：" + j); // 1 2 1 2
        }
    }
}
