package com.ysmjjsy.goya.component.web.domain;

import com.ysmjjsy.goya.component.dto.constants.DefaultConstants;
import com.ysmjjsy.goya.component.dto.domain.Entity;
import com.ysmjjsy.goya.component.dto.response.Response;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.dromara.hutool.core.tree.TreeNode;
import org.dromara.hutool.core.tree.TreeUtil;
import org.springframework.core.convert.converter.Converter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p> Description : Controller基础定义 </p>
 * <p>
 * 这里只在方法上做了泛型，主要是考虑到返回的结果数据可以是各种类型，而不一定受限于某一种类型。
 *
 * @author goya
 * @since 2024/1/30 1:06
 */
public interface Controller {

    /**
     * 数据实体转换为统一响应实体
     *
     * @param domain 数据实体
     * @param <E>    {@link Entity} 子类型
     * @return {@link Response} Entity
     */
    default <E extends Entity> Response result(E domain) {
        if (ObjectUtils.isNotEmpty(domain)) {
            return Response.data(domain);
        } else {
            return Response.empty();
        }
    }

    /**
     * 数据列表转换为统一响应实体
     *
     * @param domains 数据实体 List
     * @param <E>     {@link Entity} 子类型
     * @return {@link Response} List
     */
    default <E extends Entity> Response result(List<E> domains) {
        if (null == domains) {
            return Response.failure("查询数据失败！");
        }

        if (CollectionUtils.isNotEmpty(domains)) {
            return Response.data("查询数据成功！", domains);
        } else {
            return Response.empty("未查询到数据！");
        }
    }

    /**
     * 数组转换为统一响应实体
     *
     * @param domains 数组
     * @param <T>     数组类型
     * @return {@link Response} List
     */
    default <T> Response result(T[] domains) {
        if (ArrayUtils.isNotEmpty(domains)) {
            return Response.data("查询数据成功！", domains);
        } else {
            return Response.empty("未查询到数据！");
        }
    }

    /**
     * 数据 Map 转换为统一响应实体
     *
     * @param map 数据 Map
     * @return {@link Response} Map
     */
    default Response result(Map<String, Object> map) {
        if (null == map) {
            return Response.failure("查询数据失败！");
        }

        if (MapUtils.isNotEmpty(map)) {
            return Response.data("查询数据成功！", map);
        } else {
            return Response.empty("未查询到数据！");
        }
    }

    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param parameter 数据ID
     * @return {@link Response} String
     */
    default Response result(String parameter) {
        if (ObjectUtils.isNotEmpty(parameter)) {
            return Response.data("操作成功!", parameter);
        } else {
            return Response.failure("操作失败!", parameter);
        }
    }

    /**
     * 数据操作结果转换为统一响应实体
     *
     * @param status 操作状态
     * @return {@link Response} String
     */
    default Response result(boolean status) {
        if (status) {
            return Response.success("操作成功!");
        } else {
            return Response.failure("操作失败!");
        }
    }

    default <E extends Entity> Response result(List<E> domains, Converter<E, TreeNode<String>> toTreeNode) {
        if (ObjectUtils.isNotEmpty(domains)) {
            List<TreeNode<String>> treeNodes = domains.stream().map(toTreeNode::convert).collect(Collectors.toList());
            return Response.data("查询数据成功", TreeUtil.build(treeNodes, DefaultConstants.TREE_ROOT_ID));
        } else {
            return Response.empty("未查询到数据！");
        }
    }


    /**
     * Page 对象转换为 Map
     *
     * @param content       数据实体 List
     * @param totalPages    分页总页数
     * @param totalElements 总的数据条目
     * @param <E>           {@link Entity} 子类型
     * @return Map
     */
    default <E extends Entity> Map<String, Object> getPageInfoMap(List<E> content, int totalPages, long totalElements) {
        Map<String, Object> result = HashMap.newHashMap(8);
        result.put("content", content);
        result.put("totalPages", totalPages);
        result.put("totalElements", totalElements);
        return result;
    }
}
