package com.ysmjjsy.goya.security.authentication.form;

import com.ysmjjsy.goya.component.web.utils.RequestUtils;
import com.ysmjjsy.goya.security.authentication.utils.SymmetricUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

/**
 * <p>Description: 表单登录 Details 定义 </p>
 *
 * @author goya
 * @since 2022/4/12 10:32
 */
@Getter
public class FormLoginWebAuthenticationDetails extends WebAuthenticationDetails {

    /**
     * 验证码是否关闭
     */
    private final Boolean closed;
    /**
     * 请求中，验证码对应的表单参数名。对应UI Properties 里面的 captcha parameter
     */
    private final String parameterName;
    /**
     * 验证码分类
     */
    private final String category;
    private String code = null;
    private String identity = null;

    public FormLoginWebAuthenticationDetails(String remoteAddress, String sessionId, Boolean closed, String parameterName, String category, String code, String identity) {
        super(remoteAddress, sessionId);
        this.closed = closed;
        this.parameterName = parameterName;
        this.category = category;
        this.code = code;
        this.identity = identity;
    }

    public FormLoginWebAuthenticationDetails(HttpServletRequest request, Boolean closed, String parameterName, String category, String code) {
        super(request);
        this.closed = closed;
        this.parameterName = parameterName;
        this.category = category;
        this.code = code;
    }

    private void init(HttpServletRequest request) {
        String encryptedCode = request.getParameter(parameterName);
        String key = request.getParameter("symmetric");

        this.identity = RequestUtils.analyseRequestId(request);

        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(encryptedCode)) {
            byte[] byteKey = SymmetricUtils.getDecryptedSymmetricKey(key);
            this.code = SymmetricUtils.decrypt(encryptedCode, byteKey);
        }
    }

}
