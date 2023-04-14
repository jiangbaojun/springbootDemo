package com.example.demo.common.i18n.message;

import java.io.Serializable;

public class Resource implements Serializable
{
    //唯一标识
    private long resourceId;
    //键名
    private String name;
    //值
    private String text;
    //对应的语言
    private String language;

    public long getResourceId()
    {
        return resourceId;
    }
    public void setResourceId(long resourceId)
    {
        this.resourceId = resourceId;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }
    public String getLanguage()
    {
        return language;
    }
    public void setLanguage(String language)
    {
        this.language = language;
    }
}