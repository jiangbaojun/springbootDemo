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

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * 自定义时间反序列换方式
 */
public class DynamicDateDeserialize extends StdDeserializer<Date> implements ContextualDeserializer {

    public static String[] DATE_TIME_FORMAT = new String[]{
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.SSS",
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
        String dateStr = p.getValueAsString();
        try {
            return DateUtils.parseDate(dateStr, DATE_TIME_FORMAT);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property) throws JsonMappingException {
        return new DynamicDateDeserialize(property);
    }
}
