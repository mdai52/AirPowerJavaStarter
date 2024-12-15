package cn.hamm.demo.module.notify.enums;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.interfaces.IDictionary;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <h1>通知场景</h1>
 *
 * @author Hamm.cn
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
@Description("通知场景")
public enum NotifyScene implements IDictionary {
    APP_SECRET_RESET(1, "应用密钥重置通知"),
    SUPPLIER_ADD(2, "新建供应商"),
    SUPPLIER_UPDATE(3, "修改供应商"),
    ;

    private final int key;
    private final String label;
}