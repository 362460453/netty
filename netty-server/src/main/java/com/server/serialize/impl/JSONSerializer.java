package com.server.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.server.serialize.Serializer;
import com.server.serialize.SerializerAlgorithm;

public class JSONSerializer implements Serializer {
    @Override
    public byte getSerializerAlogrithm() {
        
        return SerializerAlgorithm.JSON;
    } 

    @Override
    public byte[] serialize(Object object) {
        
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        
        return JSON.parseObject(bytes, clazz);
    }
}
