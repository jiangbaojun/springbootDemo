package com.example.demo.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

/**
 * 自定义时间反序列换方式
 */
public class DynamicDateDeserialize extends StdDeserializer<Date> implements ContextualDeserializer {

    //优先解析带时间的
    public static String[] DATE_TIME_FORMAT = new String[]{
            "yyyy-MM-dd HH:mm:ss.SSS",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd",
            "HH:mm:ss",
            "HH:mm"
    };

    private BeanProperty beanProperty;

    public DynamicDateDeserialize() {
        super(Date.class);
    }
    public DynamicDateDeserialize(BeanProperty beanProperty) {
        super(Date.class);
        this.beanProperty = beanProperty;
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String fieldName = p.getCurrentName();
        try {
            //支持long时间戳
            long valueAsLong = p.getValueAsLong();
            if(valueAsLong!=0){
                return new Date(valueAsLong);
            }
        }catch (Exception e){}
        TimeZone timeZone = LocaleContextHolder.getTimeZone();
        String dateStr = p.getValueAsString();
        try {
            Date date = DateUtils.parseDate(dateStr, DATE_TIME_FORMAT);
            //指定时区时间，转换为系统默认时间（通常获取时间用于数据库过滤查询，实际情况要看jdbc时区）
            return convertDate(date, timeZone.toZoneId(), ZoneId.systemDefault());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        return new DynamicDateDeserialize(property);
    }

    public static void main(String[] args) {
//        ZoneId sourceZoneId = ZoneId.ofOffset("UTC", ZoneOffset.of("+8"));
//        ZoneId sourceZoneId = ZoneId.of("Asia/Shanghai");
        ZoneId sourceZoneId = TimeZone.getTimeZone("GMT+08:00").toZoneId();
        ZoneId targetZoneId = TimeZone.getTimeZone("GMT+05:00").toZoneId();
        Date date = new DynamicDateDeserialize().convertDate(new Date(), sourceZoneId, targetZoneId);
        System.out.println(date);
    }
    public Date convertDate(Date date, ZoneId sourceZoneId, ZoneId targetZoneId){
        if(date==null){
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        ZonedDateTime zonedDateTime = localDateTime.atZone(sourceZoneId);
        ZonedDateTime targetZonedDateTime = zonedDateTime.withZoneSameInstant(targetZoneId);
        return Date.from(targetZonedDateTime.toLocalDateTime().atZone(ZoneId.systemDefault()).toInstant());
    }
}
