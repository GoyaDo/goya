package com.ysmjjsy.goya.component.common.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.ysmjjsy.goya.component.common.json.gson.GsonUtils;
import com.ysmjjsy.goya.component.dto.constants.SymbolConstants;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.hutool.core.net.url.UrlDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * <p>Description: SQL注入处理工具类 </p>
 *
 * @author goya
 * @since 2021/9/1 10:17
 */
public class SqlInjectionUtils {

    private static final Logger log = LoggerFactory.getLogger(SqlInjectionUtils.class);

    private static final String SQL_REGEX = "\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|\\/\\*.+?\\*\\/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)";

    private static final Pattern SQL_PATTERN = Pattern.compile(SQL_REGEX, Pattern.CASE_INSENSITIVE);

    /**
     * 检测参数的合规性
     *
     * @param lowerValue 待检测的原始内容
     * @param param      待检测内容
     * @return true 存在不合规内容，false 不存在不会合规内容
     */
    private static boolean matching(String lowerValue, String param) {
        if (SQL_PATTERN.matcher(param).find()) {
            log.error("[Goya] |- The parameter contains keywords {} that do not allow SQL!", lowerValue);
            return true;
        }
        return false;
    }

    /**
     * 将对象转换为小写字符串
     *
     * @param obj 对象
     * @return 如果对象为 null 则返回 null
     */
    private static String toLowerCase(Object obj) {
        //这里需要将参数转换为小写来处理
        return Optional.ofNullable(obj)
                .map(Object::toString)
                .map(String::toLowerCase)
                .orElse(null);
    }

    /**
     * 检测合规性
     *
     * @param value 待检测内容
     * @return true 存在不合规内容，false 不存在不会合规内容
     */
    private static boolean checking(Object value) {
        //这里需要将参数转换为小写来处理
        String lowerValue = toLowerCase(value);
        return Optional.ofNullable(lowerValue)
                .map(data -> matching(data, data))
                .orElse(false);
    }

    /**
     * get请求sql注入校验
     *
     * @param value 具体的检验
     * @return true 存在不合规内容，false 不存在不会合规内容
     */
    public static boolean checkForGet(String value) {

        // 参数需要url编码
        // 这里需要将参数转换为小写来处理
        // 不改变原值
        // value示例 order=asc&pageNum=1&pageSize=100&parentId=0
        String lowerValue = UrlDecoder.decode(value, StandardCharsets.UTF_8).toLowerCase();

        //获取到请求中所有参数值-取每个key=value组合第一个等号后面的值
        return Stream.of(lowerValue.split("\\&"))
                .map(kp -> kp.substring(kp.indexOf(SymbolConstants.EQUAL) + 1))
                .parallel()
                .anyMatch(param -> matching(lowerValue, param));
    }

    /**
     * post请求sql注入校验
     *
     * @param value 具体的检验
     * @return true 存在不合规内容，false 不存在不会合规内容
     */
    public static boolean checkForPost(String value) {

        List<JsonElement> result = new ArrayList<>();

        JsonElement jsonElement = GsonUtils.toJsonElement(value);
        iterator(jsonElement, result);

        return CollectionUtils.isNotEmpty(result);
    }

    private static void iterator(JsonElement jsonElement, List<JsonElement> result) {
        if (jsonElement.isJsonNull()) {
            return;
        }

        if (jsonElement.isJsonPrimitive()) {
            boolean hasInjection = checking(jsonElement.toString());
            if (hasInjection) {
                result.add(jsonElement);
            }
            return;
        }

        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            if (ObjectUtils.isNotEmpty(jsonArray)) {
                for (JsonElement je : jsonArray) {
                    iterator(je, result);
                }
            }
            return;
        }

        if (jsonElement.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> es = jsonElement.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> en : es) {
                iterator(en.getValue(), result);
            }
        }
    }

}