package org.inori.rest.kairosdbrest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.log4j.Log4j2;

import java.lang.reflect.Field;
import java.util.*;

@Log4j2
public class JsonUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setDefaultPrettyPrinter(new DefaultPrettyPrinter());
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.registerModule(new JavaTimeModule());
    }

    /**
     * 获取mapper
     * @return
     */
    public static ObjectMapper getMapper() {
        return mapper;
    }

    /**
     * 对象转化成json
     *
     * @param value
     * @return
     */
    public static String toJson(Object value) {
        log.debug("转换{}为json", value);
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("json处理异常：", e);
            return "";
        }
    }

    public static String toPrettyJson(Object value) {
        log.debug("转换{}为json", value);

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("json处理异常：", e);
            return "";
        }
    }

    /**
     * json支付床转对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T fromJson2Bean(String json, Class<T> clazz) {
        log.debug("转换为bean：{}", json);
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("Json处理异常: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 读取json为List<Map<String, String>
     * @param json json文本
     * @return
     */
    public static List<Map<String, String>> fromJson2ListMapWithStringAndString(String json) {
        log.debug("转换为bean：{}", json);
        MapType mapType = mapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, String.class);
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(LinkedList.class, mapType);

        try {
            return mapper.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            log.error("Json处理异常: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * 读取json为List<Map<String, Object>
     * @param json
     * @return
     */
    public static List<Map<String, Object>> fromJson2ListMapWithStringAndObject(String json) {
        log.debug("转换为bean：{}", json);
        MapType mapType = mapper.getTypeFactory().constructMapType(LinkedHashMap.class, String.class, Object.class);
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(LinkedList.class, mapType);

        try {
            return mapper.readValue(json, collectionType);
        } catch (JsonProcessingException e) {
            log.error("Json处理异常: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    /**
     * 妆化那位bean-list
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> fromJson2BeanList(String json, Class<T> clazz) {
        log.debug("装欢为bean集合：{}", json);
        CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);

        try {
            return mapper.readValue(json, listType);
        } catch (JsonProcessingException e) {
            log.error("Json处理异常：{}", e.getMessage(), e);
        }

        return Collections.emptyList();
    }

    /**
     * 转换成Map<String, Object>
     * @param json
     * @return
     */
    public static Map<String, Object> fromJson2MapWithStringAndObject(String json) {
        log.debug("转换为map：{}", json);
        MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, String.class, Object.class);

        try {
            return mapper.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            log.error("Json处理异常：{}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    /**
     * 转换为Map<String, Integer>
     * @param json
     */
    public static Map<String, Integer> fromJson2MapWithStringAndInteger(String json) {
        log.debug("转换为map：{}", json);
        MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, String.class, Integer.class);

        try {
            return mapper.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            log.error("Json处理异常：{}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }

    /**
     * 转换成Map<String,Double>
     * @param json
     * @return
     */
    public static Map<String, Double> fromJson2MapWithStringAndDouble(String json) {
        log.debug("转换为map：{}", json);
        MapType mapType = mapper.getTypeFactory().constructMapType(Map.class, String.class, Double.class);

        try {
            return mapper.readValue(json, mapType);
        } catch (JsonProcessingException e) {
            log.error("Json处理异常：{}", e.getMessage(), e);
            return Collections.emptyMap();
        }
    }


}
