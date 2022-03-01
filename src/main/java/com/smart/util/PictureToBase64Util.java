package com.smart.util;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: YanZanJie
 * @date: 2021/4/21 8:48
 */
public class PictureToBase64Util {

    /**
     * 本地图片转换成base64字符串
     *
     * @return
     * @author yanzj
     */
    public String imageToBase64ByLocal(MultipartFile files) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
//        InputStream in = null;

        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = files.getInputStream();
//            in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String base64Str = encoder.encode(data);
        //替换base64后的字符串中的回车换行
        base64Str.replaceAll("(\r\n|\r|\n|\n\r)", "");
        // 返回Base64编码过的字节数组字符串
        return base64Str;
    }

//    /**
//     * 本地图片转换成base64字符
//     * @return
//     * @author yanzj
//     */
//    public static String ImageToBase64ByLocal(MultipartFile files){
//        return imageToBase64ByLocal(files);
//    }

}
