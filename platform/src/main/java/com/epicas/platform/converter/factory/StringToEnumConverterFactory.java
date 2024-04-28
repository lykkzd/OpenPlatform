package com.epicas.platform.converter.factory;

import com.epicas.platform.converter.StringToEnumConverter;
import com.epicas.platform.domain.en.BaseEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyang
 * @date 2023年10月08日 16:32
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, com.epicas.platform.domain.en.BaseEnum> {

    private static final Map<Class, Converter> converters = new HashMap<>();
    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter<String, T> converter = converters.get(targetType);
        if (converter == null) {
            converter = new StringToEnumConverter(targetType);
            converters.put(targetType,converter);
        }
        return converter;
    }
}
