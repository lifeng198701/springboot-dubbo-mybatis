package com.lifeng.commons.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.lifeng.commons.util.DateUtil;

import java.io.Writer;
import java.util.Optional;

/**
 * @author: lifeng
 * @date 2014年4月11日 下午4:53:27
 */
public abstract class JsonConverter
{
    public static final ObjectMapper mapper = new ObjectMapper();

    static
    {
        // 序列化时候，只序列化非空字段
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setDateFormat(DateUtil.DEFAULT_DATETIME_FORMATER.get());
        // 当反序列化出现未定义字段时候，不出现错误
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    }

    public static String format(Object obj)
    {
        try
        {
            return mapper.writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException("object format to json error:" + obj, e);
        }
    }

    public static void outputToWriter(Writer out, Object value)
    {
        try
        {
            mapper.writeValue(out, value);
        }
        catch (Exception e)
        {
            throw new RuntimeException("output to writer error:" + value, e);
        }
    }

    /*
     * JsonNode反转为bean的时候，bean必须有缺省的构造函数，不然json直接用clz.getConstructor时候，无法找到默认构造
     */
    public static <T> T parse(JsonNode body, Class<T> clz)
    {
        try
        {
            return mapper.readValue(body.traverse(), clz);
        }
        catch (Exception e)
        {
            throw new RuntimeException("json node parse to object [" + clz + "] error:" + body, e);
        }
    }

    public static <T> T parse(String str, Class<T> clz)
    {
        try
        {
            return mapper.readValue(str == null ? "{}" : str, clz);
        }
        catch (Exception e)
        {
            throw new RuntimeException("json parse to object [" + clz + "] error:" + str, e);
        }
    }

    public static <T> T parse(Optional<String> json, Class<T> clz)
    {
        return json.map((str) -> parse(str, clz)).orElse(null);
    }

    public static <T> T parse(String str, TypeReference<T> tr)
    {
        try
        {
            return mapper.readValue(str, tr);
        }
        catch (Exception e)
        {
            throw new RuntimeException("json parse to object [" + tr + "] error:" + str, e);
        }
    }

    public static <T> T parse(JsonNode body, JavaType javaType)
    {
        try
        {
            return mapper.readValue(body.traverse(), javaType);
        }
        catch (Exception e)
        {
            throw new RuntimeException("json parse to object [" + body + "] error:" + body, e);
        }
    }

    public static <T> T parse(String str, JavaType javaType)
    {
        try
        {
            return mapper.readValue(str, javaType);
        }
        catch (Exception e)
        {
            throw new RuntimeException("json parse to object [" + str + "] error:" + str, e);
        }
    }

    public static JsonNode tree(Object obj)
    {
        try
        {
            return mapper.valueToTree(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException("object format to json error:" + obj, e);
        }
    }

    public static String serializeAllExcept(Object obj, String... filterFields)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            FilterProvider filters = new SimpleFilterProvider().addFilter(obj.getClass().getName(),
                    SimpleBeanPropertyFilter.serializeAllExcept(filterFields));
            mapper.setFilters(filters);

            mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector()
            {
                @Override
                public Object findFilterId(Annotated ac)
                {
                    return ac.getName();
                }
            });

            return mapper.writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException("object format to json error:" + obj, e);
        }
    }

    public static String filterOutAllExcept(Object obj, String... filterFields)
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

            FilterProvider filters = new SimpleFilterProvider().addFilter(obj.getClass().getName(),
                    SimpleBeanPropertyFilter.filterOutAllExcept(filterFields));
            mapper.setFilters(filters);

            mapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector()
            {
                public Object findFilterId(AnnotatedClass ac)
                {
                    return ac.getName();
                }
            });

            return mapper.writeValueAsString(obj);
        }
        catch (Exception e)
        {
            throw new RuntimeException("object format to json error:" + obj, e);
        }
    }

}
