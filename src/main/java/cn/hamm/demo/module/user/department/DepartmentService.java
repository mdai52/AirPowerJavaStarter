package cn.hamm.demo.module.user.department;

import cn.hamm.airpower.model.Sort;
import cn.hamm.airpower.model.query.QueryListRequest;
import cn.hamm.airpower.root.RootEntity;
import cn.hamm.airpower.root.delegate.TreeServiceDelegate;
import cn.hamm.demo.base.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm.cn
 */
@Slf4j
@Service
public class DepartmentService extends BaseService<DepartmentEntity, DepartmentRepository> {
    /**
     * <h3>排序字段</h3>
     */
    private static final String ORDER_FIELD_NAME = "orderNo";

    @Override
    protected void beforeDelete(long id) {
        TreeServiceDelegate.ensureNoChildrenBeforeDelete(this, id);
    }

    @Override
    protected @NotNull QueryListRequest<DepartmentEntity> beforeGetList(@NotNull QueryListRequest<DepartmentEntity> sourceRequestData) {
        DepartmentEntity filter = sourceRequestData.getFilter();
        sourceRequestData.setSort(Objects.requireNonNullElse(
                sourceRequestData.getSort(),
                new Sort().setField(ORDER_FIELD_NAME)
        ));
        sourceRequestData.setFilter(filter);
        return sourceRequestData;
    }

    @Override
    protected @NotNull List<DepartmentEntity> afterGetList(@NotNull List<DepartmentEntity> list) {
        list.forEach(RootEntity::excludeBaseData);
        return list;
    }
}
