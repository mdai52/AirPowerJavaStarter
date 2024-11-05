package cn.hamm.demo.module.user;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Filter;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.exception.ServiceError;
import cn.hamm.airpower.helper.CookieHelper;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.util.AccessTokenUtil;
import cn.hamm.airpower.util.RandomUtil;
import cn.hamm.demo.base.BaseController;
import cn.hamm.demo.module.open.app.OpenAppEntity;
import cn.hamm.demo.module.open.app.OpenAppService;
import cn.hamm.demo.module.system.permission.PermissionEntity;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("user")
@Description("用户")
public class UserController extends BaseController<UserEntity, UserService, UserRepository> implements IUserAction {
    @Autowired
    private CookieHelper cookieHelper;

    @Autowired
    private OpenAppService openAppService;

    @Description("获取我的信息")
    @Permission(authorize = false)
    @PostMapping("getMyInfo")
    @Filter(WhenGetMyInfo.class)
    public Json getMyInfo() {
        return Json.data(service.get(getCurrentUserId()));
    }

    @Description("修改我的信息")
    @Permission(authorize = false)
    @PostMapping("updateMyInfo")
    public Json updateMyInfo(@RequestBody @Validated(WhenUpdateMyInfo.class) UserEntity user) {
        user.setId(getCurrentUserId());
        user.setRoleList(null);
        service.update(user);
        return Json.success("资料修改成功");
    }

    @Description("获取我的菜单")
    @Permission(authorize = false)
    @PostMapping("getMyMenuList")
    public Json getMyMenuList() {
        return Json.data(service.getMenuListByUserId(getCurrentUserId()));
    }

    @Description("获取我的权限")
    @Permission(authorize = false)
    @PostMapping("getMyPermissionList")
    public Json getMyPermissionList() {
        List<PermissionEntity> permissionList = service.getPermissionListByUserId(getCurrentUserId());
        List<String> permissions = new ArrayList<>();
        permissionList.forEach(permission -> permissions.add(permission.getIdentity()));
        return Json.data(permissions);
    }

    @Description("修改我的密码")
    @Permission(authorize = false)
    @PostMapping("updateMyPassword")
    public Json updateMyPassword(@RequestBody @Validated(WhenUpdateMyPassword.class) UserEntity user) {
        user.setId(getCurrentUserId());
        service.modifyUserPassword(user);
        return Json.success("密码修改成功");
    }

    @Description("找回密码")
    @Permission(login = false)
    @PostMapping("resetMyPassword")
    public Json resetMyPassword(@RequestBody @Validated(WhenResetMyPassword.class) UserEntity user) {
        service.resetMyPassword(user);
        return Json.success("密码重置成功");
    }

    @Description("注册账号")
    @Permission(login = false)
    @PostMapping("register")
    public Json register(@RequestBody @Validated(WhenRegister.class) UserEntity user) {
        service.register(user);
        return Json.success("注册成功");
    }

    @Description("账号密码登录")
    @Permission(login = false)
    @PostMapping("login")
    public Json login(@RequestBody @Validated(WhenLogin.class) UserEntity user, HttpServletResponse httpServletResponse) {
        return doLogin(UserLoginType.VIA_ACCOUNT_PASSWORD, user, httpServletResponse);
    }

    @Description("邮箱验证码登录")
    @Permission(login = false)
    @PostMapping("loginViaEmail")
    public Json loginViaEmail(@RequestBody @Validated(WhenLoginViaEmail.class) UserEntity user, HttpServletResponse httpServletResponse) {
        return doLogin(UserLoginType.VIA_EMAIL_CODE, user, httpServletResponse);
    }

    /**
     * <h1>处理用户登录</h1>
     *
     * @param userLoginType 登录方式
     * @param user          登录数据
     * @param response      响应的请求
     * @return JsonData
     */
    private Json doLogin(@NotNull UserLoginType userLoginType, UserEntity user, HttpServletResponse response) {
        String accessToken = "";
        switch (userLoginType) {
            case VIA_ACCOUNT_PASSWORD -> accessToken = service.login(user);
            case VIA_EMAIL_CODE -> accessToken = service.loginViaEmail(user);
            default -> ServiceError.SERVICE_ERROR.show("暂不支持的登录方式");
        }

        // 开始处理Oauth2登录逻辑
        Long userId = AccessTokenUtil.create().getPayloadId(accessToken, serviceConfig.getAccessTokenSecret());

        // 存储Cookies
        String cookieString = RandomUtil.randomString();
        service.saveCookie(userId, cookieString);
        response.addCookie(cookieHelper.getAuthorizeCookie(cookieString));

        String appKey = user.getAppKey();
        if (!StringUtils.hasText(appKey)) {
            return Json.data(accessToken, "登录成功,请存储你的访问凭证");
        }

        // 验证应用信息
        OpenAppEntity openAppEntity = openAppService.getByAppKey(appKey);
        ServiceError.PARAM_INVALID.whenNull(openAppEntity, "登录失败,错误的应用ID");

        // 生成临时身份令牌code
        String code = RandomUtil.randomString();
        openAppEntity.setCode(code);

        // 缓存临时身份令牌code
        service.saveOauthCode(userId, openAppEntity);
        return Json.data(code, "登录成功,请重定向此Code");
    }
}
