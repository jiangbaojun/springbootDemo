package com.example.demo.common;

import com.example.demo.util.SpringContextHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 自定义时间序列换方式
 */
public class DynamicDateSerialize extends StdSerializer<Date> implements ContextualSerializer {
    private BeanProperty beanProperty;

    public DynamicDateSerialize() {
        super(Date.class);
    }

    public DynamicDateSerialize(BeanProperty beanProperty) {
        super(Date.class);
        this.beanProperty = beanProperty;
    }

    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (date == null) {
            jsonGenerator.writeString("");
            return;
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            TimeZone timeZone = null;
            String datePattern = null;
            //优先属性注解
            JsonFormat jsonFormat = beanProperty.getMember().getAnnotation(JsonFormat.class);
            if(jsonFormat!=null && jsonFormat.timezone()!=null){
                try {
                    datePattern = jsonFormat.pattern();
                    timeZone = TimeZone.getTimeZone(jsonFormat.timezone());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(timeZone==null){
                //从LocalContext中获取
                TimeZoneAwareLocaleContext timeZoneAwareLocaleContext = (TimeZoneAwareLocaleContext) SpringContextHolder.getBean(CookieLocaleResolver.class).resolveLocaleContext(request);
                timeZone = timeZoneAwareLocaleContext.getTimeZone();
            }
            //可以考虑从用户信息获取。本例从head中获取
            if(datePattern==null) {
                datePattern = request.getHeader("x-date-pattern");
            }
            if(datePattern==null){
                datePattern = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
            dateFormat.setTimeZone(timeZone);

            String result = dateFormat.format(date);
            jsonGenerator.writeString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) {
        return new DynamicDateSerialize(property);
    }

}
