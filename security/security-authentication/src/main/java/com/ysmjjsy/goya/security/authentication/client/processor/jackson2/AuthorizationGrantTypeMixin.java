package com.ysmjjsy.goya.security.authentication.client.processor.jackson2;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * <p>Description: AuthorizationGrantTypesMixin </p>
 *
 * @author goya
 * @since 2022/10/24 15:57
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE,
        isGetterVisibility = JsonAutoDetect.Visibility.NONE)
public class AuthorizationGrantTypeMixin {

    @JsonCreator
    AuthorizationGrantTypeMixin(@JsonProperty("value") String value) {
    }
}


