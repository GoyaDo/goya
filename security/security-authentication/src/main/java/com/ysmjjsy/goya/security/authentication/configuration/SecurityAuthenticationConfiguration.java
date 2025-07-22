package com.ysmjjsy.goya.security.authentication.configuration;

import com.ysmjjsy.goya.security.authentication.client.configuration.OAuth2ClientConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/7/22 17:30
 */
@Slf4j
@RequiredArgsConstructor
@AutoConfiguration
@Import(OAuth2ClientConfiguration.class)
public class SecurityAuthenticationConfiguration {
}
