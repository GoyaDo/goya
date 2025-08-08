package com.ysmjjsy.goya.component.web.scan;

import com.ysmjjsy.goya.component.pojo.constants.SymbolConstants;
import com.ysmjjsy.goya.component.web.domain.RequestMapping;
import com.ysmjjsy.goya.component.web.configuration.properties.ScanProperties;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Description: RequestMapping 扫描器抽象定义 </p>
 * <p>
 * 提取 RequestMapping 扫描共性内容，方便维护
 *
 * @author goya
 * @since 2024/1/31 23:38
 */
public abstract class AbstractRequestMappingScanner implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(AbstractRequestMappingScanner.class);

    private final ScanProperties scanProperties;
    private final RequestMappingScanEventManager requestMappingScanEventManager;

    protected AbstractRequestMappingScanner(ScanProperties scanProperties, RequestMappingScanEventManager requestMappingScanEventManager) {
        this.scanProperties = scanProperties;
        this.requestMappingScanEventManager = requestMappingScanEventManager;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        ApplicationContext applicationContext = event.getApplicationContext();

        log.debug("[Goya] |- [1] Application is READY, start to scan request mapping!");

        onApplicationEvent(applicationContext);
    }

    protected abstract void onApplicationEvent(ApplicationContext applicationContext);

    /**
     * 检测RequestMapping是否需要被排除
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    protected boolean isExcludedRequestMapping(HandlerMethod handlerMethod) {
        if (!isSpringAnnotationMatched(handlerMethod)) {
            return true;
        }

        return !isSwaggerAnnotationMatched(handlerMethod);
    }

    /**
     * 如果开启isJustScanRestController，那么就只扫描RestController
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    protected boolean isSpringAnnotationMatched(HandlerMethod handlerMethod) {
        if (Boolean.TRUE.equals(scanProperties.getJustScanRestController())) {
            return handlerMethod.getMethod().getDeclaringClass().getAnnotation(RestController.class) != null;
        }

        return true;
    }

    /**
     * 有ApiIgnore注解的方法不扫描, 没有ApiOperation注解不扫描
     *
     * @param handlerMethod HandlerMethod
     * @return boolean
     */
    protected boolean isSwaggerAnnotationMatched(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethodAnnotation(Hidden.class) != null) {
            return false;
        }

        Operation operation = handlerMethod.getMethodAnnotation(Operation.class);
        return ObjectUtils.isNotEmpty(operation) && !operation.hidden();
    }

    /**
     * 如果当前class的groupId在GroupId列表中，那么就进行扫描，否则就排除
     *
     * @param className 当前扫描的controller类名
     * @return Boolean
     */
    protected boolean isLegalGroup(String className) {
        if (StringUtils.isNotEmpty(className)) {
            List<String> groupIds = scanProperties.getScanGroupIds();
            List<String> result = groupIds.stream().filter(groupId -> StringUtils.contains(className, groupId)).collect(Collectors.toList());
            return CollectionUtils.sizeIsEmpty(result);
        } else {
            return true;
        }
    }

    /**
     * 根据 url 和 method 生成与当前 url 对应的 code。
     * <p>
     * 例如：
     * 1. POST /element 生成为 post:element
     * 2. /open/identity/session 生成为 open:identity:session
     *
     * @param url            请求 url
     * @param requestMethods 请求 method。
     * @return url 对应的 code
     */
    protected String createCode(String url, String requestMethods) {
        String[] search = new String[]{SymbolConstants.OPEN_CURLY_BRACE, SymbolConstants.CLOSE_CURLY_BRACE, SymbolConstants.FORWARD_SLASH};
        String[] replacement = new String[]{SymbolConstants.BLANK, SymbolConstants.BLANK, SymbolConstants.COLON};
        String code = StringUtils.replaceEach(url, search, replacement);

        String result = StringUtils.isNotBlank(requestMethods) ? StringUtils.lowerCase(requestMethods) + code : StringUtils.removeStart(code, SymbolConstants.COLON);
        log.trace("[Goya] |- Create code [{}] for Request [{}] : [{}]", result, requestMethods, url);
        return result;
    }

    /**
     * 判断当前环境是否符合扫描的条件设定
     *
     * @return 是否执行扫描
     */
    protected boolean notExecuteScanning() {
        return !requestMappingScanEventManager.isPerformScan();
    }

    /**
     * 扫描完成操作
     *
     * @param serviceId 服务ID
     * @param resources 扫描到的资源
     */
    protected void complete(String serviceId, List<RequestMapping> resources) {
        if (CollectionUtils.isNotEmpty(resources)) {
            log.debug("[Goya] |- [2] Request mapping scan found [{}] resources in service [{}], go to next stage!", serviceId, resources.size());
            requestMappingScanEventManager.postProcess(new RequestMappingEvent(this,resources));
        } else {
            log.debug("[Goya] |- [2] Request mapping scan can not find any resources in service [{}]!", serviceId);
        }

        log.info("[Goya] |- Request Mapping Scan for Service: [{}] FINISHED!", serviceId);
    }
}
