package com.example.demo.common.conditional;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.annotation.MergedAnnotations;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE + 40)
public class MyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        MergedAnnotations annotations = metadata.getAnnotations();
        if (!annotations.isPresent(MyConditionAnnotation.class)) {
            return true;
        }
        //MultiValueMap<String, Object> attributesMap = metadata.getAllAnnotationAttributes(MyConditionAnnotation.class.getName());
        //List<AnnotationAttributes> allAnnotationAttributes = annotationAttributesFromMultiValueMap(attributesMap);
        MergedAnnotation<MyConditionAnnotation> annotation = annotations.get(MyConditionAnnotation.class);
        // 获取注解的属性值
        String key = annotation.getValue("key", String.class).orElse("");
        String[] values = annotation.getValue("values", String[].class).orElse(new String[]{});

        //模拟values包含key对应的属性值
        String propertyValue = environment.getProperty(key);
        for (int i = 0; i < values.length; i++) {
            String value = values[i];
            if (value != null && value.equals(propertyValue)) {
                return true;
            }
        }
        return false;
    }

    private List<AnnotationAttributes> annotationAttributesFromMultiValueMap(MultiValueMap<String, Object> multiValueMap) {
        List<Map<String, Object>> maps = new ArrayList<>();
        multiValueMap.forEach((key, value) -> {
            for (int i = 0; i < value.size(); i++) {
                Map<String, Object> map;
                if (i < maps.size()) {
                    map = maps.get(i);
                }
                else {
                    map = new HashMap<>();
                    maps.add(map);
                }
                map.put(key, value.get(i));
            }
        });
        List<AnnotationAttributes> annotationAttributes = new ArrayList<>(maps.size());
        for (Map<String, Object> map : maps) {
            annotationAttributes.add(AnnotationAttributes.fromMap(map));
        }
        return annotationAttributes;
    }
}