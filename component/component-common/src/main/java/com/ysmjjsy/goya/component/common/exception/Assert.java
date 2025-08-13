package com.ysmjjsy.goya.component.common.exception;

import com.ysmjjsy.goya.component.common.exception.argument.GoyaIllegalArgumentException;

import java.util.Collection;
import java.util.Map;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 13:57
 */
public abstract class Assert {


    /**
     * Assert a boolean expression, throwing {@code GoyaIllegalArgumentException}
     * <p>
     * for example
     *
     * <pre class="code">Assert.isTrue(i != 0, errorCode.B_ORDER_illegalNumber, "The order number can not be zero");</pre>
     *
     * @param expression a boolean expression
     * @param errMessage the exception message to use if the assertion fails
     * @throws GoyaIllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(boolean expression, String errMessage) {
        if (!expression) {
            throw new GoyaIllegalArgumentException(errMessage);
        }
    }

    /**
     * Assert a boolean expression, if expression is true, throwing {@code GoyaIllegalArgumentException}
     * <p>
     * for example
     *
     * <pre class="code">Assert.isFalse(i == 0, errorCode.B_ORDER_illegalNumber, "The order number can not be zero");</pre>
     * <p>
     * This is more intuitive than isTure.
     */
    public static void isFalse(boolean expression, String errMessage) {
        if (expression) {
            throw new GoyaIllegalArgumentException(errMessage);
        }
    }

    public static void isTrue(boolean expression) {
        isTrue(expression, "[Assertion failed] Must be true");
    }

    public static void isFalse(boolean expression) {
        isFalse(expression, "[Assertion failed] Must be false");
    }

    public static void notNull(Object object, String errMessage) {
        if (object == null) {
            throw new GoyaIllegalArgumentException(errMessage);
        }
    }

    public static void notNull(Object object) {
        notNull(object, "[Assertion failed] Must not null");
    }

    public static void notEmpty(Collection<?> collection, String errMessage) {
        if (collection == null || collection.isEmpty()) {
            throw new GoyaIllegalArgumentException(errMessage);
        }
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, "[Assertion failed] Collection must not be empty: it must contain at least 1 element");
    }

    public static void notEmpty(Map<?, ?> map, String errMessage) {
        if (map == null || map.isEmpty()) {
            throw new GoyaIllegalArgumentException(errMessage);
        }
    }

    public static void notEmpty(Map<?, ?> map) {
        notEmpty(map, "[Assertion failed] Map must not be empty: it must contain at least one entry");
    }
}
