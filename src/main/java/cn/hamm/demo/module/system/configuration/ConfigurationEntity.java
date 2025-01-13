package cn.hamm.demo.module.system.configuration;

import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.ReadOnly;
import cn.hamm.airpower.annotation.Search;
import cn.hamm.airpower.config.Constant;
import cn.hamm.demo.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

/**
 * <h1>配置信息实体</h1>
 *
 * @author Hamm.cn
 */
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "configuration")
@Description("配置信息")
public class ConfigurationEntity extends BaseEntity<ConfigurationEntity> {
    @Description("配置标识")
    @Column(columnDefinition = "varchar(255) comment '规则字段'", unique = true)
    @NotBlank(groups = {WhenUpdate.class, WhenAdd.class}, message = "配置标识不能为空")
    private String flag;

    @Description("配置的值")
    @Column(columnDefinition = "varchar(255) default '' comment '字符串值'")
    private String configuration;

    @Description("内置配置")
    @Search(Search.Mode.EQUALS)
    @ReadOnly
    @Column(columnDefinition = "tinyint UNSIGNED default 0 comment '内置配置'")
    private Boolean isSystem;

    /**
     * <h3>设置是系统内置配置</h3>
     *
     * @param isSystem 内置配置
     * @return 配置信息
     */
    public ConfigurationEntity setIsSystem(Boolean isSystem) {
        this.isSystem = isSystem;
        return this;
    }

    @Transient
    public Boolean getBooleanConfiguration() {
        if (Objects.isNull(configuration)) {
            return false;
        }
        return Constant.ONE_STRING.equals(configuration);
    }

    @Transient
    public Long getNumberConfiguration() {
        if (Objects.isNull(configuration)) {
            return Constant.ZERO_LONG;
        }
        return Long.parseLong(configuration);
    }
}
