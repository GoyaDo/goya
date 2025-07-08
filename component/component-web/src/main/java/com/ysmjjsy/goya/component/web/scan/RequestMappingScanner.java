package com.ysmjjsy.goya.component.web.scan;

import com.ysmjjsy.goya.component.common.resolver.PropertyResolver;
import com.ysmjjsy.goya.component.dto.constants.GoyaConstants;
import com.ysmjjsy.goya.component.dto.constants.SymbolConstants;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import com.ysmjjsy.goya.component.web.properties.ScanProperties;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.crypto.SecureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>Description: RequestMapping扫描器 </p>
 *
 * @author goya
 * @since 2020/6/2 19:52
 */
public class RequestMappingScanner extends AbstractRequestMappingScanner {

    private static final Logger log = LoggerFactory.getLogger(RequestMappingScanner.class);

    public RequestMappingScanner(ScanProperties scanProperties, RequestMappingScanEventManager requestMappingScanEventManager) {
        super(scanProperties, requestMappingScanEventManager);
    }

    @Override
    public void onApplicationEvent(ApplicationContext applicationContext) {
        // 1、获取服务ID：该服务ID对于微服务是必需的。
        String serviceId = PropertyResolver.getProperty(applicationContext.getEnvironment(), GoyaConstants.ITEM_SPRING_APPLICATION_NAME);

        // 2、只针对有EnableResourceServer注解的微服务进行扫描。如果变为单体架构目前不会用到EnableResourceServer所以增加的了一个Architecture判断
        if (notExecuteScanning()) {
            // 只扫描资源服务器
            log.warn("[Goya] |- Can not found scan annotation in Service [{}], Skip!", serviceId);
            return;
        }

        // 3、获取所有接口映射
        Map<String, RequestMappingHandlerMapping> mappings = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);

        // 4、 获取url与类和方法的对应信息
        List<RequestMapping> resources = new ArrayList<>();
        for (RequestMappingHandlerMapping mapping : mappings.values()) {
            Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
            if (MapUtils.isNotEmpty(handlerMethods)) {
                // 4.1、如果是被排除的requestMapping，那么就进行不扫描
                // 4.2、拼装扫描信息
                handlerMethods.forEach((requestMappingInfo, handlerMethod) -> {
                    if (isExcludedRequestMapping(handlerMethod)) {
                        return;
                    }
                    RequestMapping requestMapping = createRequestMapping(serviceId, requestMappingInfo, handlerMethod);
                    if (ObjectUtils.isEmpty(requestMapping)) {
                        return;
                    }
                    resources.add(requestMapping);
                });
            }
        }

        complete(serviceId, resources);
    }

    private RequestMapping createRequestMapping(String serviceId, RequestMappingInfo info, HandlerMethod method) {
        // 4.2.1、获取类名
        // method.getMethod().getDeclaringClass().getName() 取到的是注解实际所在类的名字，比如注解在父类叫BaseController，那么拿到的就是BaseController
        // method.getBeanType().getName() 取到的是注解实际Bean的名字，比如注解在在父类叫BaseController，而实际类是SysUserController，那么拿到的就是SysUserController
        String className = method.getBeanType().getName();

        // 4.2.2、检测该类是否在GroupIds列表中
        if (isLegalGroup(className)) {
            return null;
        }

        // 5.2.3、获取不包含包路径的类名
        String classSimpleName = method.getBeanType().getSimpleName();

        // 4.2.4、获取RequestMapping注解对应的方法名
        String methodName = method.getMethod().getName();

        // 5.2.5、获取注解对应的请求类型
        RequestMethodsRequestCondition requestMethodsRequestCondition = info.getMethodsCondition();
        String requestMethods = StringUtils.join(requestMethodsRequestCondition.getMethods(), SymbolConstants.COMMA);

        // 5.2.6、获取主机对应的请求路径
        PathPatternsRequestCondition pathPatternsCondition = info.getPathPatternsCondition();
        Set<String> patternValues = pathPatternsCondition.getPatternValues();
        if (CollectionUtils.isEmpty(patternValues)) {
            return null;
        }

        String urls = StringUtils.join(patternValues, SymbolConstants.COMMA);

        // 5.2.8、根据serviceId, requestMethods, urls生成的MD5值，作为自定义主键
        String flag = serviceId + SymbolConstants.DASH + requestMethods + SymbolConstants.DASH + urls;
        String id = SecureUtil.md5(flag);

        // 5.2.9、组装对象
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setMappingId(id);
        requestMapping.setMappingCode(createCode(urls, requestMethods));
        requestMapping.setServiceId(serviceId);
        Operation apiOperation = method.getMethodAnnotation(Operation.class);
        if (ObjectUtils.isNotEmpty(apiOperation)) {
            requestMapping.setDescription(apiOperation.summary());
        }
        requestMapping.setRequestMethod(requestMethods);
        requestMapping.setUrl(urls);
        requestMapping.setClassName(className);
        requestMapping.setMethodName(methodName);
        return requestMapping;
    }
}
