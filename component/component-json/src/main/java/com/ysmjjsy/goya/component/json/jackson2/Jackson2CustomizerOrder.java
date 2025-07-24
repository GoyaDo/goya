package com.ysmjjsy.goya.component.json.jackson2;

/**
 * <p>Description: Jackson2 ObjectMapper builder Customizer 顺序控制 </p>
 * <p>
 * 值越小越先执行
 *
 * @author goya
 * @since 2023/4/29 16:30
 */
public interface Jackson2CustomizerOrder {

    int CUSTOMIZER_DEFAULT = 1;

    int CUSTOMIZER_XSS = CUSTOMIZER_DEFAULT + 1;
}
