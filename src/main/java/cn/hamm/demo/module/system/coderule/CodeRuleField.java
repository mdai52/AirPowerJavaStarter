package cn.hamm.demo.module.system.coderule;

import cn.hamm.airpower.config.Constant;
import cn.hamm.airpower.interfaces.IDictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>编码规则表格枚举</h1>
 *
 * @author Hamm.cn
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum CodeRuleField implements IDictionary {
    DEPARTMENT_CODE(1, "部门编码", "DP", SerialNumberUpdate.NEVER, Constant.EMPTY_STRING),
    ;

    private final int key;
    private final String label;

    /**
     * <h3>默认前缀</h3>
     */
    private final String defaultPrefix;

    /**
     * <h3>默认序列号类型</h3>
     */
    private final SerialNumberUpdate defaultSnType;

    /**
     * <h3>默认模板</h3>
     */
    private final String defaultTemplate;
}
