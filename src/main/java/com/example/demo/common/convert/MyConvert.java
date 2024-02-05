package com.example.demo.common.convert;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.converter.GenericConverter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MyConvert implements ConditionalGenericConverter {

    @Override
    public Set<GenericConverter.ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new GenericConverter.ConvertiblePair(String.class, List.class));
    }

    @Override
    public Object convert(Object srcVal, TypeDescriptor srcTD, TypeDescriptor tgtTD) {
        String srcString = (String) srcVal;
        String[] split = srcString.split(",");
        List<String> collect = Arrays.stream(split).map(s->"prefix-"+s).collect(Collectors.toList());
        return collect;
    }

    /**
     * 只会判断一次，会有缓存
     * 详见：org.springframework.core.convert.support.GenericConversionService#getConverter(org.springframework.core.convert.TypeDescriptor, org.springframework.core.convert.TypeDescriptor)
     */
    @Override
    public boolean matches(TypeDescriptor srcTD, TypeDescriptor tgtTD) {
        MyConvertAnnotation annotation = tgtTD.getAnnotation(MyConvertAnnotation.class);
        return srcTD.getType() == String.class && tgtTD.getType() == List.class && annotation!=null;
    }
}
