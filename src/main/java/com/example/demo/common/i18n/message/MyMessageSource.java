package com.example.demo.common.i18n.message;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.text.MessageFormat;
import java.util.*;

public class MyMessageSource extends AbstractMessageSource implements ResourceLoaderAware{
    private ResourceLoader resourceLoader;

    /**
     * Map切分字符
     */
    protected final String MAP_SPLIT_CODE = "|";
    private final Map<String, String> properties = new HashMap<String, String>();

    public MyMessageSource()
    {
        reload();
    }

    public void reload() {
        properties.clear();
        properties.putAll(loadTexts());
    }

    /**
     * 查询数据 虚拟数据，可以从数据库读取信息，此处省略
     * @return
     */
    private List<Resource> getResource(){
        List<Resource> resources = new ArrayList<Resource>();
        Resource re = new Resource();
        Resource re1 = new Resource();
        re.setResourceId(1);
        re.setName("username");
        re.setText("xiaoming_my");
        re.setLanguage("en");
        resources.add(re);
        re1.setResourceId(2);
        re1.setName("username");
        re1.setText("小明my");
        re1.setLanguage("zh_CN");
        resources.add(re1);
        return resources;
    }

    /**
     * 加载数据
     */
    protected Map<String, String> loadTexts() {
        Map<String, String> mapResource = new HashMap<String, String>();
        List<Resource> resources = this.getResource();
        for (Resource item : resources) {
            String code = item.getName() + MAP_SPLIT_CODE + item.getLanguage();
            mapResource.put(code, item.getText());
        }
        return mapResource;
    }

    private String getText(String code, Locale locale) {
        String localeCode = locale.toString();
        String key = code + MAP_SPLIT_CODE + localeCode;
        String localeText = properties.get(key);
        String resourceText = code;

        if(localeText != null) {
            resourceText = localeText;
        }
        else {
            localeCode = Locale.ENGLISH.getLanguage();
            key = code + MAP_SPLIT_CODE + localeCode;
            localeText = properties.get(key);
            if(localeText != null) {
                resourceText = localeText;
            }
            else {
                try {
                    if(getParentMessageSource() != null) {
                        resourceText = getParentMessageSource().getMessage(code, null, locale);
                    }
                } catch (Exception e) {
                    logger.error("Cannot find message with code: " + code);
                }
            }
        }
        return resourceText;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader)
    {
        this.resourceLoader = (resourceLoader != null ? resourceLoader : new DefaultResourceLoader());
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale)
    {
        String msg = getText(code, locale);
        MessageFormat result = createMessageFormat(msg, locale);
        return result;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        String result = getText(code, locale);
        return result;
    }
}