package com.ysmjjsy.goya.module.jpa.naming;

import com.ysmjjsy.goya.component.common.constants.RegexPoolConstants;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

import java.io.Serial;

/**
 * <p>Description: 使用hbm2ddl自动创建表时，默认将@Colume中的信息转换为小写，小写的字段名称与其它的字段标准不同（驼峰式，单词首字母大写） 复写原始类，生成符合标准的字段名称。</p>
 *
 * @author goya
 * @since 2019/11/15 10:34
 */
public class GoyaPhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Serial
    private static final long serialVersionUID = 8032744715941407901L;

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        if (name == null) {
            return null;
        }

        // 如果手动指定了列名，则直接返回
        if (name.isQuoted()) {
            return name;
        }

        // 使用正则常量替换
        String newName = name.getText()
                .replaceAll(RegexPoolConstants.CAMEL_TO_UNDERSCORE, "$1_$2")
                .toLowerCase();

        return new Identifier(newName, false);
    }
}
