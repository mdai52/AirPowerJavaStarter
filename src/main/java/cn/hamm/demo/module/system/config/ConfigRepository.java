package cn.hamm.demo.module.system.config;

import cn.hamm.demo.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * <h1>数据库连接信息</h1>
 *
 * @author Hamm.cn
 */
@Repository
public interface ConfigRepository extends BaseRepository<ConfigEntity> {
    /**
     * <h3>根据标识查询配置信息</h3>
     *
     * @param flag 配置标识
     * @return 配置信息
     */
    ConfigEntity getByFlag(String flag);
}
