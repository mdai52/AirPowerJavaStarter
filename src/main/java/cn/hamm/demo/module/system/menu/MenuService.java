package cn.hamm.demo.module.system.menu;

import cn.hamm.airpower.model.Sort;
import cn.hamm.airpower.model.query.QueryListRequest;
import cn.hamm.airpower.root.RootEntity;
import cn.hamm.airpower.root.delegate.TreeServiceDelegate;
import cn.hamm.demo.base.BaseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <h1>Service</h1>
 *
 * @author Hamm.cn
 */
@Service
public class MenuService extends BaseService<MenuEntity, MenuRepository> {
    /**
     * <h3>排序字段</h3>
     */
    private static final String ORDER_FIELD_NAME = "orderNo";

    @Override
    protected void beforeDelete(long id) {
        TreeServiceDelegate.ensureNoChildrenBeforeDelete(this, id);
    }

    @Override
    protected @NotNull QueryListRequest<MenuEntity> beforeGetList(@NotNull QueryListRequest<MenuEntity> sourceRequestData) {
        MenuEntity filter = sourceRequestData.getFilter();
        sourceRequestData.setSort(Objects.requireNonNullElse(
                sourceRequestData.getSort(),
                new Sort().setField(ORDER_FIELD_NAME)
        ));
        sourceRequestData.setFilter(filter);
        return sourceRequestData;
    }

    @Override
    protected @NotNull List<MenuEntity> afterGetList(@NotNull List<MenuEntity> list) {
        list.forEach(RootEntity::excludeBaseData);
        return list;
    }

    /**
     * <h3>加载菜单</h3>
     */
    public final void loadMenu() {
        MenuEntity exist = getMaybeNull(1L);
        if (Objects.nonNull(exist)) {
            return;
        }

        MenuEntity firstMenu, secondMenu;
        firstMenu = new MenuEntity().setName("首页").setOrderNo(99).setPath("/console").setComponent("/console/index/index").setParentId(0L);
        add(firstMenu);

        // 人事管理
        firstMenu = new MenuEntity().setName("人事管理").setOrderNo(88).setParentId(0L);
        firstMenu = get(add(firstMenu));

        secondMenu = new MenuEntity().setName("用户管理").setPath("/console/user/list").setParentId(firstMenu.getId());
        add(secondMenu);

        secondMenu = new MenuEntity().setName("角色管理").setPath("/console/role/list").setParentId(firstMenu.getId());
        add(secondMenu);

        secondMenu = new MenuEntity().setName("部门管理").setPath("/console/department/list").setParentId(firstMenu.getId());
        add(secondMenu);

        // 开放能力
        firstMenu = new MenuEntity().setName("开放能力").setOrderNo(10).setParentId(0L);
        firstMenu = get(add(firstMenu));

        secondMenu = new MenuEntity().setName("我的应用").setPath("/console/open/app/list").setParentId(firstMenu.getId());
        add(secondMenu);

        secondMenu = new MenuEntity().setName("通知管理").setPath("/console/open/notify/list").setParentId(firstMenu.getId());
        add(secondMenu);

        // 系统设置
        firstMenu = new MenuEntity().setName("系统设置").setOrderNo(2).setParentId(0L);
        firstMenu = get(add(firstMenu));

        secondMenu = new MenuEntity().setName("权限管理").setPath("/console/permission/list").setParentId(firstMenu.getId());
        add(secondMenu);

        secondMenu = new MenuEntity().setName("菜单管理").setPath("/console/menu/list").setParentId(firstMenu.getId());
        add(secondMenu);
    }

}
