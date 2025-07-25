package org.hibernate.cache.spi;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.query.spi.Limit;
import org.hibernate.query.spi.QueryParameterBindings;

import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * <p>Description: 分页缓存 </p>
 *
 * @author goya
 * @since 2023/1/1 21:13
 */
public class QueryKey implements Serializable {
    /**
     * todo (6.0) : integrate work from original 6.0 branch
     */
    public interface ParameterBindingsMemento extends Serializable {
    }

    public static QueryKey from(
            String sqlQueryString,
            Limit limit,
            QueryParameterBindings parameterBindings,
            SharedSessionContractImplementor persistenceContext) {
        // todo (6.0) : here is where we should centralize cacheable-or-not
        //		if this method returns null, the query should be considered un-cacheable
        //
        // todo (6.0) : should limited (first/max) results be cacheable?
        // todo (6.0) : should filtered results be cacheable?

        final Limit limitToUse = limit == null ? Limit.NONE : limit;

        return new QueryKey(
                sqlQueryString,
                parameterBindings.generateQueryKeyMemento( persistenceContext ),
                limitToUse.getFirstRow(),
                limitToUse.getMaxRows(),
                persistenceContext.getTenantIdentifier(),
                persistenceContext.getLoadQueryInfluencers().getEnabledFilterNames()
        );
    }


    private final String sqlQueryString;
    private final ParameterBindingsMemento parameterBindingsMemento;
    private final Integer firstRow;
    private final Integer maxRows;
    private final String tenantIdentifier;
    private final String[] enabledFilterNames;

    /**
     * For performance reasons, the hashCode is cached; however, it is marked transient so that it can be
     * recalculated as part of the serialization process which allows distributed query caches to work properly.
     */
    private transient int hashCode;

    public QueryKey(
            String sql,
            ParameterBindingsMemento parameterBindingsMemento,
            Integer firstRow,
            Integer maxRows,
            String tenantIdentifier,
            Set<String> enabledFilterNames) {
        this.sqlQueryString = sql;
        this.parameterBindingsMemento = parameterBindingsMemento;
        this.firstRow = firstRow;
        this.maxRows = maxRows;
        this.tenantIdentifier = tenantIdentifier;
        this.enabledFilterNames = enabledFilterNames.toArray( String[]::new );
        this.hashCode = generateHashCode();
    }

    /**
     * Deserialization hook used to re-init the cached hashcode which is needed for proper clustering support.
     *
     * @param in The object input stream.
     *
     * @throws IOException Thrown by normal deserialization
     * @throws ClassNotFoundException Thrown by normal deserialization
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.hashCode = generateHashCode();
    }

    private int generateHashCode() {
        int result = 13;
        result = 37 * result + sqlQueryString.hashCode();
        // Don't include the firstRow and maxRows in the hash as these values are rarely useful for query caching
		result = 37 * result + ( firstRow==null ? 0 : firstRow );
		result = 37 * result + ( maxRows==null ? 0 : maxRows );
        result = 37 * result + ( tenantIdentifier==null ? 0 : tenantIdentifier.hashCode() );
        result = 37 * result + parameterBindingsMemento.hashCode();
        result = 37 * result + Arrays.hashCode( enabledFilterNames );
        return result;
    }

    @Override
    @SuppressWarnings({"RedundantIfStatement", "EqualsWhichDoesntCheckParameterClass"})
    public boolean equals(Object other) {
        // it should never be another type, so skip the instanceof check and just do the cast
        final QueryKey that;

        try {
            that = (QueryKey) other;
        }
        catch (ClassCastException cce) {
            // treat this as the exception case
            return false;
        }

        if ( ! Objects.equals( sqlQueryString, that.sqlQueryString ) ) {
            return false;
        }

        if ( ! Objects.equals( tenantIdentifier, that.tenantIdentifier ) ) {
            return false;
        }

        if ( ! Objects.equals( firstRow, that.firstRow )
                || ! Objects.equals( maxRows, that.maxRows ) ) {
            return false;
        }

        // Set's `#equals` impl does a deep check, so `Objects#equals` is a good check
        if ( ! Objects.equals( parameterBindingsMemento, that.parameterBindingsMemento ) ) {
            return false;
        }

        if ( ! Arrays.equals( enabledFilterNames, that.enabledFilterNames ) ) {
            return false;
        }

        return true;
    }



    @Override
    public int hashCode() {
        return hashCode;
    }
}