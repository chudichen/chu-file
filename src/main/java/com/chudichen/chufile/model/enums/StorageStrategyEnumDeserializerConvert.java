package com.chudichen.chufile.model.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.lang.annotation.Annotation;

/**
 * @author chudichen
 * @date 2021-01-25
 */
public class StorageStrategyEnumDeserializerConvert implements Converter<String, StorageStrategyEnum> {

    @Override
    public StorageStrategyEnum convert(@NonNull String s) {
        return StorageStrategyEnum.getEnum(s);
    }
}
