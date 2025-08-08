package com.ysmjjsy.goya.security.authentication.client.processor.jackson2;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ysmjjsy.goya.component.json.jackson2.Jackson2Constants;
import com.ysmjjsy.goya.security.authentication.form.FormLoginWebAuthenticationDetails;
import com.ysmjjsy.goya.security.core.domain.GoyaGrantedAuthority;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 * <p>Description: 自定义 User Details Module </p>
 *
 * @author goya
 * @since 2022/2/17 23:39
 */
public class GoyaJackson2Module extends SimpleModule {

    public GoyaJackson2Module() {
        super(GoyaJackson2Module.class.getName(), Jackson2Constants.VERSION);
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(GoyaUser.class, GoyaUserMixin.class);
        context.setMixInAnnotations(GoyaGrantedAuthority.class, GoyaGrantedAuthorityMixin.class);
        context.setMixInAnnotations(FormLoginWebAuthenticationDetails.class, FormLoginWebAuthenticationDetailsMixin.class);
    }
}
