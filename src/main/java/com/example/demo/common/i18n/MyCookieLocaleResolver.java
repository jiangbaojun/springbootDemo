package com.example.demo.common.i18n;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.TimeZone;

public class MyCookieLocaleResolver extends CookieLocaleResolver {
    //重写构造方法,改变cookie信息
    public MyCookieLocaleResolver(){
        this.setCookieName("locale");
        //cookie有效期30天
        this.setCookieMaxAge(30*24*60*60);
        System.out.println("设置cookie参数成功！");
    }

    /**
     * 设置时区。
     * json转换，涉及时间字段时，手动获取
     */
    @Override
    protected TimeZone determineDefaultTimeZone(HttpServletRequest request) {
        //获取动态时区，。本例从head中获取，实际上也可以从登录用户信息、参数、cookie等方式获取
        //请注意，如果在配置类中全局设置。需要额外考虑从外部接口（非数据库）获得的时间数据
        // 把数据库中获得的时间数据，转换为对应时区的时间。
        // 注意数据库jdbc连接上的时区配置，建议使用0时区。数据库插入，使用的是jdbc连接上的时区.相当于数据库存储0时区时间，取出展示时，根据不同时区做转换
        String timeZoneNum = request.getHeader("x-time-zone");
        String timeZoneId = "GMT"+timeZoneNum;
        if(StringUtils.isEmpty(timeZoneNum)){
            timeZoneId = "GMT+8";
        }
        TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);
        return timeZone;
//        return super.determineDefaultTimeZone(request);
    }
}
