package com.epicas.platform.converter;

import com.epicas.platform.domain.en.BaseEnum;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyang
 * @date 2023年10月08日 16:32
 */
public class IntegerToEnumConverter<T extends BaseEnum> implements Converter<Integer,T> {

    private Map<Integer,T> enumMap = new HashMap<>();

    public IntegerToEnumConverter(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        for (T e : enums) {
            enumMap.put(e.getCode(),e);
        }
    }

    @Override
    public T convert(Integer source) {
        T t = enumMap.get(source);
        if (t == null){
            throw new IllegalArgumentException("无法匹配对应的枚举类型");
        }
        return t;
    }
}
