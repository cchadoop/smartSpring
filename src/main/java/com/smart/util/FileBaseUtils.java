package com.smart.util;

import cn.hutool.core.util.RandomUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: YanZanJie
 * @date: 2021/4/27 10:03
 * 文件基础工具类
 */
public class FileBaseUtils {

    //创建文件
    public String creadFile(String fileName, String uri) throws IOException {
        File testFile = new File(uri + File.separator + RandomUtil.randomString(10) + File.separator + fileName);
        File fileParent = testFile.getParentFile();//返回的是File类型,可以调用exsit()等方法
        String fileParentPath = testFile.getParent();//返回的是String类型
        if (!fileParent.exists()) {
            fileParent.mkdirs();// 能创建多级目录
        }
        if (!testFile.exists()) {
            testFile.createNewFile();//有路径才能创建文件
        }
        String absolutePath = testFile.getAbsolutePath();//得到文件/文件夹的绝对路径


        return absolutePath;

    }

    //读取文件内容
    public List<String> read(String filePath) throws IOException {
        // 使用ArrayList来存储每行读取到的字符串
        FileInputStream fis = new FileInputStream(filePath);
        RandomAccessFile raf = new RandomAccessFile(new File(filePath), "r");
        String s;
        List<String> userList = new ArrayList<String>();
        while ((s = raf.readLine()) != null) {
            userList.add(new String(s.getBytes("ISO-8859-1"), "utf-8"));
        }
        return userList;
    }


    //写入文件内容
    public boolean writer(String filePath, String content) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        FileWriter fw = new FileWriter(file, true);

        fw.write(content + "\r\n");

        fw.flush();
        if (fw != null) {
            fw.close();
        }
        return true;
    }

    //删除文件
    public boolean delFile(String filePath) {
        File file = new File(filePath);
        boolean delFile = false;
        if (file.exists()) {
            delFile = file.delete();
        } else {
            delFile = false;
        }
        return delFile;
    }


}
