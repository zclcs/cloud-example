package com.zclcs.common.dict.jackson;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.zclcs.common.dict.json.DictTextJsonSerializer;
import com.zclcs.common.dict.json.annotation.DictText;

/**
 * 自定义 SerializerProvider ，主要是为了处理数据字典的 null 值处理问题
 *
 * @author zclcs
 */
public class DictDefaultSerializerProviderImpl extends DefaultSerializerProvider {
    public DictDefaultSerializerProviderImpl() {
    }

    public DictDefaultSerializerProviderImpl(final SerializerProvider src, final SerializationConfig config, final SerializerFactory f) {
        super(src, config, f);
    }

    public DictDefaultSerializerProviderImpl(final DefaultSerializerProvider src) {
        super(src);
    }

    @Override
    public DefaultSerializerProvider createInstance(final SerializationConfig config, final SerializerFactory jsf) {
        return new DictDefaultSerializerProviderImpl(this, config, jsf);
    }

    /**
     * 重写此方法，为了给使用了 {@link DictText} 注解的字段自定义的 null 值序列化器
     *
     * @param property BeanProperty
     * @return JsonSerializer
     * @throws JsonMappingException 异常
     */
    @Override
    public JsonSerializer<Object> findNullValueSerializer(final BeanProperty property) throws JsonMappingException {
        final JsonSerializer<Object> serializer = DictTextJsonSerializer.getJsonSerializer(property);
        if (serializer != null) {
            return serializer;
        }
        return super.findNullValueSerializer(property);
    }
}
