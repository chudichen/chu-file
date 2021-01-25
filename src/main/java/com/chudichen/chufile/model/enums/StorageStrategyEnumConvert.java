package com.chudichen.chufile.model.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 存储策略，默认为LOCAL
 *
 * @author chudichen
 * @date 2021-01-22
 */
@Converter(autoApply = true)
public class StorageStrategyEnumConvert implements AttributeConverter<StorageStrategyEnum, String> {

    @Override
    public String convertToDatabaseColumn(StorageStrategyEnum attribute) {
        return attribute.name();
    }

    @Override
    public StorageStrategyEnum convertToEntityAttribute(String dbData) {
        return StorageStrategyEnum.getEnum(dbData);
    }
}
