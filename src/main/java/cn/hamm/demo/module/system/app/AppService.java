package cn.hamm.demo.module.system.app;

import cn.hamm.airpower.enums.ServiceError;
import cn.hamm.airpower.util.Utils;
import cn.hamm.demo.base.BaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm.cn
 */
@Service
public class AppService extends BaseService<AppEntity, AppRepository> {
    /**
     * <h2>通过AppKey获取一个应用</h2>
     *
     * @param appKey AppKey
     * @return AppEntity
     */
    public AppEntity getByAppKey(String appKey) {
        AppEntity appEntity = repository.getByAppKey(appKey);
        ServiceError.DATA_NOT_FOUND.whenNull(appEntity, "没有查到指定AppKey的应用");
        return appEntity;
    }

    /**
     * <h2>通过应用ID重置秘钥</h2>
     *
     * @param id 应用ID
     * @return 应用新秘钥
     */
    public String resetSecretById(Long id) {
        String newSecret = Utils.getRandomUtil().randomString().toUpperCase();
        AppEntity entity = get(id);
        entity.setAppSecret(newSecret);
        update(entity);
        return newSecret;
    }

    @Override
    protected @NotNull AppEntity beforeSaveToDatabase(@NotNull AppEntity app) {
        return app.setAppSecret(Objects.requireNonNullElse(
                app.getAppSecret(),
                Utils.getRandomUtil().randomString().toUpperCase()
        ));
    }
}
