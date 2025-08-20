package com.ysmjjsy.goya.module.mybatisplus.helper;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.ysmjjsy.goya.component.common.exception.definition.GoyaRuntimeException;
import com.ysmjjsy.goya.component.core.context.SpringContextHolder;
import com.ysmjjsy.goya.module.mybatisplus.enums.DataBaseType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库助手
 *
 * @author goya
 * @since 2025/7/22 23:42
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataBaseHelper {

    private static final DynamicRoutingDataSource DS = SpringContextHolder.getBean(DynamicRoutingDataSource.class);

    /**
     * 获取当前数据库类型
     */
    public static DataBaseType getDataBaseType() {
        DataSource dataSource = DS.determineDataSource();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData metaData = conn.getMetaData();
            String databaseProductName = metaData.getDatabaseProductName();
            return DataBaseType.find(databaseProductName);
        } catch (SQLException e) {
            throw new GoyaRuntimeException(e.getMessage());
        }
    }

    public static boolean isMySql() {
        return DataBaseType.MY_SQL == getDataBaseType();
    }

    public static boolean isOracle() {
        return DataBaseType.ORACLE == getDataBaseType();
    }

    public static boolean isPostgerSql() {
        return DataBaseType.POSTGRE_SQL == getDataBaseType();
    }

    public static boolean isSqlServer() {
        return DataBaseType.SQL_SERVER == getDataBaseType();
    }

    public static String findInSet(Object var1, String var2) {
        DataBaseType dataBasyType = getDataBaseType();
        String var = (String) var1;
        if (dataBasyType == DataBaseType.SQL_SERVER) {
            // charindex(',100,' , ',0,100,101,') <> 0
            return "charindex(',%s,' , ','+%s+',') <> 0".formatted(var, var2);
        } else if (dataBasyType == DataBaseType.POSTGRE_SQL) {
            // (select strpos(',0,100,101,' , ',100,')) <> 0
            return "(select strpos(','||%s||',' , ',%s,')) <> 0".formatted(var2, var);
        } else if (dataBasyType == DataBaseType.ORACLE) {
            // instr(',0,100,101,' , ',100,') <> 0
            return "instr(','||%s||',' , ',%s,') <> 0".formatted(var2, var);
        }
        // find_in_set(100 , '0,100,101')
        return "find_in_set('%s' , %s) <> 0".formatted(var, var2);
    }

    /**
     * 获取当前加载的数据库名
     */
    public static List<String> getDataSourceNameList() {
        return new ArrayList<>(DS.getDataSources().keySet());
    }
}
