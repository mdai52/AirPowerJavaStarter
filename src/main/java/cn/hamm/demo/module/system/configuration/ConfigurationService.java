package cn.hamm.demo.module.system.configuration;

import cn.hamm.demo.base.BaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * <h1>Service</h1>
 *
 * @author Hamm.cn
 */
@Service
public class ConfigurationService extends BaseService<ConfigurationEntity, ConfigurationRepository> {
    /**
     * <h3>根据配置枚举获取配置信息</h3>
     *
     * @param configurationFlag 配置枚举
     * @return 配置信息
     */
    public final ConfigurationEntity getByFlag(@NotNull ConfigurationFlag configurationFlag) {
        return repository.getByFlag(configurationFlag.name());
    }

    /**
     * <h3>根据配置标识获取配置信息</h3>
     *
     * @param flag 配置标识
     * @return 配置信息
     */
    public final ConfigurationEntity getByFlag(@NotNull String flag) {
        return repository.getByFlag(flag);
    }

    @Override
    protected ConfigurationEntity beforeAppSaveToDatabase(@NotNull ConfigurationEntity configuration) {
        String flag = configuration.getFlag();
        // 如果 Configuration枚举中包含这个标识 则设置为系统标识
        configuration.setIsSystem(false);
        if (Arrays.stream(ConfigurationFlag.values()).anyMatch(configurationFlagEnum -> configurationFlagEnum.name().equals(flag))) {
            configuration.setIsSystem(true);
            switch ()
        }
        return configuration;
    }
}
