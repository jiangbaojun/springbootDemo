package com.example.demo.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;


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
//            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            //获取动态时区，。本例从head中获取，实际上也可以从登录用户信息、参数、cookie等方式获取
            //请注意，如果在配置类中全局设置。需要额外考虑从外部接口（非数据库）获得的时间数据
            String timeZoneNum = request.getHeader("x-time-zone");
            String timeZoneId = "GMT"+timeZoneNum;
            if(StringUtils.isEmpty(timeZoneNum)){
                timeZoneId = "GMT+8";
            }
            DateTimeZone timeZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone(timeZoneId));
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
            // 把数据库中获得的时间数据，转换为对应时区的时间。
            // 注意数据库jdbc连接上的时区配置，建议使用0时区。数据库插入，使用的是jdbc连接上的时区.相当于数据库存储0时区时间，取出展示时，根据不同时区做转换
            String result = format.withZone(timeZone).print(date.getTime());
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
