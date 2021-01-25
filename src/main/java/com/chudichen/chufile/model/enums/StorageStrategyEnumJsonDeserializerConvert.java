package com.chudichen.chufile.model.enums;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author chudichen
 * @date 2021-01-25
 */
public class StorageStrategyEnumJsonDeserializerConvert extends JsonDeserializer<StorageStrategyEnum> {

    @Override
    public StorageStrategyEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return StorageStrategyEnum.getEnum(jsonParser.getText());
    }
}
