package cn.hamm.demo.module.system.configuration;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.demo.base.BaseController;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("configuration")
@Description("系统配置")
public class ConfigurationController extends BaseController<ConfigurationEntity, ConfigurationService, ConfigurationRepository> {
}
