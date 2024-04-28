package com.epicas.platform.converter.factory;

import com.epicas.platform.converter.IntegerToEnumConverter;
import com.epicas.platform.domain.en.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyang
 * @date 2023年10月08日 16:32
 */
public class IntegerToEnumConverterFactory implements ConverterFactory<Integer, BaseEnum> {

    private static final Map<Class,Converter> converters = new HashMap<>();
    @Override
    public <T extends BaseEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
        Converter<Integer, T> converter = converters.get(targetType);
        if (converter == null) {
            converter = new IntegerToEnumConverter(targetType);
            converters.put(targetType,converter);
        }
        return converter;
    }
}
