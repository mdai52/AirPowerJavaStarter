package cn.hamm.demo.module.system.config;

import cn.hamm.airpower.interfaces.IDictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>系统配置</h1>
 *
 * @author Hamm.cn
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum ConfigFlag implements IDictionary {
    AUTO_REGISTER_EMAIL_LOGIN(1, "邮箱登录时自动注册", ConfigType.BOOLEAN, "1");

    private final int key;
    private final String label;
    private final ConfigType type;
    private final String defaultValue;
}
