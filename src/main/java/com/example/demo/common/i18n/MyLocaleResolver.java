package com.example.demo.common.i18n;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 自定义LocaleResolver
 * @author jiangbaojun
 * @date 2023/4/13 9:35
 */
public class MyLocaleResolver extends AbstractLocaleContextResolver {

    @Override
    public LocaleContext resolveLocaleContext(final HttpServletRequest request) {
        LocaleContext localeContext = new TimeZoneAwareLocaleContext() {
            @Override
            public Locale getLocale() {
                String myLang = request.getParameter("my_lang");
                Locale locale = StringUtils.parseLocale(myLang);
                return locale;
            }

            @Override
            public TimeZone getTimeZone() {
                String timeZoneNum = request.getHeader("x-time-zone");
                String timeZoneId = "GMT"+timeZoneNum;
                if(StringUtils.isEmpty(timeZoneNum)){
                    timeZoneId = "GMT+8";
                }
                TimeZone timeZone = StringUtils.parseTimeZoneString(timeZoneId);
                return timeZone;
            }
        };
        return localeContext;
    }

    @Override
    public void setLocaleContext(HttpServletRequest request, HttpServletResponse response, LocaleContext localeContext) {
        System.out.println(localeContext);
    }

}
