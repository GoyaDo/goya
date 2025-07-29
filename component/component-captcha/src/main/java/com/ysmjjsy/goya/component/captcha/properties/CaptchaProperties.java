package com.ysmjjsy.goya.component.captcha.properties;

import com.ysmjjsy.goya.component.captcha.enums.CaptchaCharacter;
import com.ysmjjsy.goya.component.captcha.enums.CaptchaFont;
import com.ysmjjsy.goya.component.captcha.enums.FontStyle;
import com.ysmjjsy.goya.component.pojo.constants.GoyaConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Description: 验证码配置参数 </p>
 *
 * @author goya
 * @since 2021/12/11 12:25
 */
@Data
@ConfigurationProperties(prefix = GoyaConstants.PROPERTY_ASSISTANT_CAPTCHA)
public class CaptchaProperties {

    private Graphics graphics = new Graphics();
    /**
     * 水印配置
     */
    private Watermark watermark = new Watermark();
    /**
     * 滑块拼图验证码配置
     */
    private Jigsaw jigsaw = new Jigsaw();
    /**
     * 文字点选验证码配置
     */
    private WordClick wordClick = new WordClick();

    @Data
    public static class Graphics {
        /**
         * 验证码字符个数
         */
        private int length = 5;
        /**
         * 验证码显示宽度
         */
        private int width = 130;
        /**
         * 验证码显示高度
         */
        private int height = 48;
        /**
         * 算数类型验证码算法复杂度
         */
        private int complexity = 2;
        /**
         * 字符类型
         */
        private CaptchaCharacter letter = CaptchaCharacter.NUM_AND_CHAR;

        private CaptchaFont font = CaptchaFont.LEXOGRAPHER;

    }

    /**
     * 右下角水印文字(我的水印)
     */
    @Data
    public static class Watermark {
        /**
         * 水印内容
         */
        private String content = GoyaConstants.SYSTEM_NAME;
        /**
         * 水印字体
         */
        private String fontName = "WenQuanZhengHei.ttf";
        /**
         * 字体样式： 0:PLAIN; 1:BOLD; 2:ITALI；
         */
        private FontStyle fontStyle = FontStyle.BOLD;

        /**
         * 水印文字中，汉字的大小，默认：25
         */
        private Integer fontSize = 25;

    }

    /**
     * 拼图滑块验证码
     */
    @Data
    public static class Jigsaw {
        /**
         * 拼图滑块验证码原图资源路径，格式：classpath:/xxx
         */
        private String originalResource = "classpath*:images/jigsaw/original/*.png";
        /**
         * 拼图滑块验证码拼图模版资源路径，格式：classpath:/xxx
         */
        private String templateResource = "classpath*:images/jigsaw/template/*.png";

        /**
         * 滑动干扰项, 可选值为(0/1/2), 默认值为：0，即无干扰项
         */
        private Integer interference = 0;

        /**
         * 偏差值，滑动结果与标准结果间可接受的偏差值。默认：5
         */
        private Integer deviation = 5;

    }

    /**
     * 文字点选验证码
     */
    @Data
    public static class WordClick {

        /**
         * 文字点选验证码资源路径，格式：classpath:/xxx
         */
        private String imageResource = "classpath*:images/word-click/*.png";

        /**
         * 文字点选验证码文字个数
         */
        private Integer wordCount = 5;
        /**
         * 随机颜色
         */
        private boolean randomColor = true;
        /**
         * 字体样式： 0:PLAIN; 1:BOLD; 2:ITALI；
         */
        private FontStyle fontStyle = FontStyle.BOLD;
        /**
         * 水印字体
         */
        private String fontName = "WenQuanZhengHei.ttf";
        /**
         * 文字点选验证码资源路径字体大小
         */
        private Integer fontSize = 25;

    }
}

