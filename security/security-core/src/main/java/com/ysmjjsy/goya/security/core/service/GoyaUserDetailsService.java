package com.ysmjjsy.goya.security.core.service;

import com.ysmjjsy.goya.module.identity.domain.AccessPrincipal;
import com.ysmjjsy.goya.module.identity.domain.UserPrincipal;
import com.ysmjjsy.goya.module.identity.user.StrategyUserService;
import com.ysmjjsy.goya.security.core.domain.GoyaUser;
import com.ysmjjsy.goya.security.core.service.converter.UserToGoyaUserConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * <p>Description: UserDetailsService核心类 </p>
 *
 * @author goya
 * @since 2019/11/25 11:02
 */
@Slf4j
public class GoyaUserDetailsService implements EnhanceUserDetailsService {

    private final StrategyUserService strategyUserService;
    private final Converter<UserPrincipal, GoyaUser> userConverter;

    public GoyaUserDetailsService(StrategyUserService strategyUserService) {
        this.strategyUserService = strategyUserService;
        userConverter = new UserToGoyaUserConverter();
    }

    @Override
    public GoyaUser loadGoyaUserBySocial(String source, AccessPrincipal accessPrincipal) throws UsernameNotFoundException {
        GoyaUser goyaUser = convert(strategyUserService.loadUserBySocial(StringUtils.toRootUpperCase(source), accessPrincipal));
        log.debug("[Goya] |- UserDetailsService loaded social user : [{}]", goyaUser.getUsername());
        return goyaUser;
    }

    @Override
    public GoyaUser loadGoyaUserByUsername(String username) throws UsernameNotFoundException {
        GoyaUser goyaUser = convert(strategyUserService.loadUserByUsername(username));
        log.debug("[Goya] |- UserDetailsService loaded user : [{}]", username);
        return goyaUser;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loadGoyaUserByUsername(username);
    }

    private GoyaUser convert(UserPrincipal userPrincipal) {
        return userConverter.convert(userPrincipal);
    }
}
