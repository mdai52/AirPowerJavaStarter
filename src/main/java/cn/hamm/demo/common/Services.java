package cn.hamm.demo.common;

import cn.hamm.demo.common.config.AppConfig;
import cn.hamm.demo.module.chat.member.MemberService;
import cn.hamm.demo.module.chat.room.RoomService;
import cn.hamm.demo.module.open.app.OpenAppService;
import cn.hamm.demo.module.open.log.OpenLogService;
import cn.hamm.demo.module.open.notify.NotifyService;
import cn.hamm.demo.module.system.coderule.CodeRuleService;
import cn.hamm.demo.module.system.log.LogService;
import cn.hamm.demo.module.system.menu.MenuService;
import cn.hamm.demo.module.system.permission.PermissionService;
import cn.hamm.demo.module.user.UserService;
import cn.hamm.demo.module.user.role.RoleService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * <h1>公共服务</h1>
 *
 * @author Hamm.cn
 */
@Service
public class Services {
    @Getter
    private static Environment environment;

    @Getter
    private static AppConfig appConfig;

    @Getter
    private static UserService userService;

    @Getter
    private static RoleService roleService;

    @Getter
    private static PermissionService permissionService;

    @Getter
    private static MenuService menuService;

    @Getter
    private static LogService logService;

    @Getter
    private static OpenAppService openAppService;

    @Getter
    private static OpenLogService openLogService;

    @Getter
    private static NotifyService notifyService;

    @Getter
    private static CodeRuleService codeRuleService;

    @Getter
    private static RoomService roomService;

    @Getter
    private static MemberService memberService;

    @Autowired
    public Services(
            Environment environment,
            AppConfig appConfig,
            UserService userService,
            RoleService roleService,
            PermissionService permissionService,
            MenuService menuService,
            LogService logService,
            OpenAppService openAppService,
            OpenLogService openLogService,
            NotifyService notifyService,
            CodeRuleService codeRuleService,
            RoomService roomService,
            MemberService memberService
    ) {
        Services.environment = environment;
        Services.appConfig = appConfig;
        Services.userService = userService;
        Services.roleService = roleService;
        Services.permissionService = permissionService;
        Services.menuService = menuService;
        Services.logService = logService;
        Services.openAppService = openAppService;
        Services.openLogService = openLogService;
        Services.notifyService = notifyService;
        Services.codeRuleService = codeRuleService;
        Services.roomService = roomService;
        Services.memberService = memberService;
    }
}
