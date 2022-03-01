package com.smart.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;

/**
 * @author LiaoQinZhou
 * @date: 2021/4/13 17:47
 */
@Configuration
public class JsonConfig {
    @Bean
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        //设置json解析中一些细节，日期格式，数据编码
        config.setDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        config.setCharset(Charset.forName(CharsetUtil.UTF_8));
        //是否在生成的Json中输出类名
        //是否输出value为null的数据
        //生成的json格式化
        //空集合输出[]而非null
        //空字符串输出“”而非null等配置
//        config.setSerializerFeatures(
//                SerializerFeature.WriteClassName,
//                SerializerFeature.WriteMapNullValue,
//                SerializerFeature.PrettyFormat,
//                SerializerFeature.WriteNullListAsEmpty,
//                SerializerFeature.WriteNullStringAsEmpty
//        );

        converter.setFastJsonConfig(config);
        return converter;
    }
}
