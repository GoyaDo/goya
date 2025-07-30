package com.ysmjjsy.goya.module.jpa.naming;

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

        // Hibernate 默认使用 Identifier.getCanonicalName()的值最为最终的值，text是原始值。如果quoted为true则使用text，否则就进行小写转换。所以此处quoted设置为true。参见具体方法。
        return new Identifier(name.getText(), true);
    }
}
