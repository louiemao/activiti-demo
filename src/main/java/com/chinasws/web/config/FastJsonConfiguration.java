package com.chinasws.web.config;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.alibaba.fastjson.support.springfox.SwaggerJsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/9.
 */
@Configuration
public class FastJsonConfiguration {
    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        List<MediaType> fastMedisTypes = new ArrayList<>();
        fastMedisTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastMedisTypes.add(MediaType.TEXT_PLAIN);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMedisTypes);

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.QuoteFieldNames,//输出key时是否使用双引号
                SerializerFeature.WriteMapNullValue,//是否输出值为null的字段
                SerializerFeature.DisableCircularReferenceDetect//关闭循环引用检测
        );
        fastJsonConfig.setFeatures(
                Feature.AllowArbitraryCommas,
                Feature.AllowUnQuotedFieldNames,
                Feature.DisableCircularReferenceDetect
        );
        //增加对swagger的支持,高版本的fastjson不再需要进行这个配置
//        fastJsonConfig.getSerializeConfig().put(Json.class, SwaggerJsonSerializer.instance);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        return fastJsonHttpMessageConverter;
    }
}
