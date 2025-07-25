package com.ysmjjsy.goya.security.authorization.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.web.util.UrlPathHelper;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/8 23:42
 */
@Slf4j
@Data
public class GoyaRequestMatcher implements RequestMatcher, Serializable {

    @Serial
    private static final long serialVersionUID = -5433048197496408279L;

    private static final String MATCH_ALL = "/**";

    private Matcher matcher;

    private String pattern;

    private String httpMethod;

    private boolean caseSensitive;

    private boolean hasWildcard;

    private UrlPathHelper urlPathHelper;

    public GoyaRequestMatcher() {
    }

    /**
     * Creates a matcher with the specific pattern which will match all HTTP methods in a
     * case sensitive manner.
     *
     * @param pattern the ant pattern to use for matching
     */
    public GoyaRequestMatcher(String pattern) {
        this(pattern, null);
    }

    public GoyaRequestMatcher(GoyaRequest request) {
        this(request.getPattern(), request.getHttpMethod());
    }

    /**
     * Creates a matcher with the supplied pattern and HTTP method in a case sensitive
     * manner.
     *
     * @param pattern    the ant pattern to use for matching
     * @param httpMethod the HTTP method. The {@code matches} method will return false if
     *                   the incoming request doesn't have the same method.
     */
    public GoyaRequestMatcher(String pattern, String httpMethod) {
        this(pattern, httpMethod, true);
    }

    /**
     * Creates a matcher with the supplied pattern which will match the specified Http
     * method
     *
     * @param pattern       the ant pattern to use for matching
     * @param httpMethod    the HTTP method. The {@code matches} method will return false if
     *                      the incoming request doesn't doesn't have the same method.
     * @param caseSensitive true if the matcher should consider case, else false
     */
    public GoyaRequestMatcher(String pattern, String httpMethod, boolean caseSensitive) {
        this(pattern, httpMethod, caseSensitive, null);
    }

    /**
     * Creates a matcher with the supplied pattern which will match the specified Http
     * method
     *
     * @param pattern       the ant pattern to use for matching
     * @param httpMethod    the HTTP method. The {@code matches} method will return false if
     *                      the incoming request doesn't have the same method.
     * @param caseSensitive true if the matcher should consider case, else false
     * @param urlPathHelper if non-null, will be used for extracting the path from the
     *                      HttpServletRequest
     */
    public GoyaRequestMatcher(String pattern, String httpMethod, boolean caseSensitive,
                                   UrlPathHelper urlPathHelper) {
        Assert.hasText(pattern, "Pattern cannot be null or empty");
        this.caseSensitive = caseSensitive;
        this.hasWildcard = containSpecialCharacters(pattern);
        if (pattern.equals(MATCH_ALL) || pattern.equals("**")) {
            pattern = MATCH_ALL;
            this.matcher = null;
        } else {
            // If the pattern ends with {@code /**} and has no other wildcards or path
            // variables, then optimize to a sub-path match
            if (pattern.endsWith(MATCH_ALL)
                    && (pattern.indexOf('?') == -1 && pattern.indexOf('{') == -1 && pattern.indexOf('}') == -1)
                    && pattern.indexOf("*") == pattern.length() - 2) {
                this.matcher = new SubPathMatcher(pattern.substring(0, pattern.length() - 3), caseSensitive);
            } else {
                this.matcher = new SpringAntMatcher(pattern, caseSensitive);
            }
        }
        this.pattern = pattern;
        this.httpMethod = checkHttpMethod(httpMethod);
        this.urlPathHelper = urlPathHelper;
    }

    private String checkHttpMethod(String method) {
        if (StringUtils.isNotBlank(method)) {
            HttpMethod httpMethod = HttpMethod.valueOf(method);
            if (ObjectUtils.isNotEmpty(httpMethod)) {
                return httpMethod.name();
            }
        }
        return null;
    }

    private boolean containSpecialCharacters(String source) {
        if (StringUtils.isNotBlank(source)) {
            return StringUtils.containsAny(source, new String[]{"*", "?", "{"});
        }
        return false;
    }

    /**
     * Returns true if the configured pattern (and HTTP-Method) match those of the
     * supplied request.
     *
     * @param request the request to match against. The ant pattern will be matched
     *                against the {@code servletPath} + {@code pathInfo} of the request.
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        if (StringUtils.isNotBlank(this.httpMethod) && StringUtils.isNotBlank(request.getMethod())
                && !StringUtils.equalsIgnoreCase(this.httpMethod, request.getMethod())) {
            return false;
        }
        if (this.pattern.equals(MATCH_ALL)) {
            return true;
        }
        String url = getRequestPath(request);
        return this.matcher.matches(url);
    }

    public boolean matches(GoyaRequest request) {

        if (StringUtils.isNotBlank(this.httpMethod) && StringUtils.isNotBlank(request.getHttpMethod())
                && !StringUtils.equalsIgnoreCase(this.httpMethod, request.getHttpMethod())) {
            return false;
        }

        if (this.pattern.equals(MATCH_ALL)) {
            return true;
        }

        if (StringUtils.equals(getPattern(), request.getPattern())) {
            return true;
        }

        if (isHasWildcard() && !request.isHasWildcard()) {
            return this.matcher.matches(request.getPattern());
        }

        if (!isHasWildcard() && request.isHasWildcard()) {
            Matcher matcher = new SpringAntMatcher(request.getPattern(), this.caseSensitive);
            return matcher.matches(getPattern());
        }

        return false;
    }

    private String getRequestPath(HttpServletRequest request) {
        if (this.urlPathHelper != null) {
            return this.urlPathHelper.getPathWithinApplication(request);
        }
        String url = request.getRequestURI();
        // request.getServletPath() 会去掉 ContextPath
//        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            url = StringUtils.isNotBlank(url) ? url + pathInfo : pathInfo;
        }
        return url;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        this.hasWildcard = containSpecialCharacters(this.pattern);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoyaRequestMatcher that = (GoyaRequestMatcher) o;
        return caseSensitive == that.caseSensitive && Objects.equal(pattern, that.pattern) && Objects.equal(httpMethod, that.httpMethod);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pattern, httpMethod, caseSensitive);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("pattern", pattern)
                .add("httpMethod", httpMethod)
                .toString();
    }

    private interface Matcher extends Serializable {

        boolean matches(String path);

    }

    private static final class SpringAntMatcher implements Matcher {

        @Serial
        private static final long serialVersionUID = -3779176373267699319L;

        private AntPathMatcher antMatcher;

        private String pattern;

        public SpringAntMatcher() {
        }

        private SpringAntMatcher(String pattern, boolean caseSensitive) {
            this.pattern = pattern;
            this.antMatcher = createMatcher(caseSensitive);
        }

        private static AntPathMatcher createMatcher(boolean caseSensitive) {
            AntPathMatcher matcher = new AntPathMatcher();
            matcher.setTrimTokens(false);
            matcher.setCaseSensitive(caseSensitive);
            return matcher;
        }

        @Override
        public boolean matches(String path) {
            return this.antMatcher.match(this.pattern, path);
        }

    }

    /**
     * Optimized matcher for trailing wildcards
     */
    private static final class SubPathMatcher implements Matcher {

        @Serial
        private static final long serialVersionUID = -6952108530147817052L;

        private String subPath;

        private int length;

        private boolean caseSensitive;

        public SubPathMatcher() {
        }

        private SubPathMatcher(String subPath, boolean caseSensitive) {
            Assert.isTrue(!subPath.contains("*"), "subpath cannot contain \"*\"");
            this.subPath = caseSensitive ? subPath : subPath.toLowerCase();
            this.length = subPath.length();
            this.caseSensitive = caseSensitive;
        }

        @Override
        public boolean matches(String path) {
            if (!this.caseSensitive) {
                path = path.toLowerCase();
            }
            return path.startsWith(this.subPath) && (path.length() == this.length || path.charAt(this.length) == '/');
        }
    }
}
