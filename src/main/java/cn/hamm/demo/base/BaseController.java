package cn.hamm.demo.base;

import cn.hamm.airpower.annotation.Extends;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.enums.Api;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.model.query.QueryListRequest;
import cn.hamm.airpower.model.query.QueryPageRequest;
import cn.hamm.airpower.root.RootEntityController;
import cn.hamm.demo.common.annotation.DisableLog;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <h1>Controller</h1>
 *
 * @param <S> Service
 * @param <E> 实体
 * @param <R> 数据源
 * @author Hamm.cn
 */
@Permission
@Extends(exclude = {Api.Export, Api.QueryExport})
public class BaseController<E extends BaseEntity<E>, S extends BaseService<E, R>, R extends BaseRepository<E>> extends RootEntityController<E, S, R> {
    @DisableLog
    @Override
    public Json getList(@RequestBody QueryListRequest<E> queryListRequest) {
        return super.getList(queryListRequest);
    }

    @DisableLog
    @Override
    public Json getPage(@RequestBody QueryPageRequest<E> queryPageRequest) {
        return super.getPage(queryPageRequest);
    }

    @DisableLog
    @Override
    public Json getDetail(@RequestBody @Validated(WhenIdRequired.class) @NotNull E entity) {
        return super.getDetail(entity);
    }
}
