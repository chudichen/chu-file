package com.chudichen.chufile.model.enums;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author chudichen
 * @date 2021-01-25
 */
public class StorageStrategyEnumSerializerConvert extends JsonSerializer<StorageStrategyEnum> {
    @Override
    public void serialize(StorageStrategyEnum strategyEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(strategyEnum.name());
    }
}
