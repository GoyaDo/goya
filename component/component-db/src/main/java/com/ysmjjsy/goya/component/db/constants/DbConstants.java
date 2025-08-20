package com.ysmjjsy.goya.component.db.constants;


import com.ysmjjsy.goya.component.common.constants.GoyaConstants;

import static com.ysmjjsy.goya.component.common.constants.SymbolConstants.ANNOTATION_PREFIX;
import static com.ysmjjsy.goya.component.common.constants.SymbolConstants.ANNOTATION_SUFFIX;

/**
 * <p>Description: 数据常量 </p>
 *
 * @author goya
 * @since 2022/1/19 18:10
 */
public interface DbConstants {

    String PROPERTY_PREFIX_DB = GoyaConstants.GOYA + ".db";

    String AREA_PREFIX = "data:";
    String ITEM_SPRING_SQL_INIT_PLATFORM = "spring.sql.init.platform";
    String PROPERTY_PREFIX_MULTI_TENANT = GoyaConstants.PROPERTY_PREFIX_DB + ".multi-tenant";
    String ITEM_DATA_DATA_SOURCE = GoyaConstants.PROPERTY_PREFIX_DB + ".data-source";
    String ITEM_MULTI_TENANT_APPROACH = PROPERTY_PREFIX_MULTI_TENANT + ".approach";

    String ANNOTATION_SQL_INIT_PLATFORM = ANNOTATION_PREFIX + ITEM_SPRING_SQL_INIT_PLATFORM + ANNOTATION_SUFFIX;

    String CORE_AREA_PREFIX = AREA_PREFIX + "core:";
    String REGION_SYS_TENANT_DATASOURCE = CORE_AREA_PREFIX + "sys:tenant:datasource";
}
