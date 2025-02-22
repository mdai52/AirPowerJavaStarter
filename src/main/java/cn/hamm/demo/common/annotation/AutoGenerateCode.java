package cn.hamm.demo.common.annotation;

import cn.hamm.demo.module.system.coderule.CodeRuleField;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h1>自动生成编码</h1>
 *
 * @author Hamm.cn
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoGenerateCode {
    /**
     * <h3>使用的自定义编码规则枚举项</h3>
     */
    CodeRuleField value();
}
