package cn.hamm.demo.module.system.oauth2;

import cn.hamm.airpower.annotation.ApiController;
import cn.hamm.airpower.annotation.Description;
import cn.hamm.airpower.annotation.Permission;
import cn.hamm.airpower.config.Configs;
import cn.hamm.airpower.config.Constant;
import cn.hamm.airpower.enums.ServiceError;
import cn.hamm.airpower.model.Json;
import cn.hamm.airpower.root.RootController;
import cn.hamm.airpower.util.Utils;
import cn.hamm.demo.common.config.AppConfig;
import cn.hamm.demo.module.system.app.AppEntity;
import cn.hamm.demo.module.system.app.AppService;
import cn.hamm.demo.module.system.app.IAppAction;
import cn.hamm.demo.module.user.UserEntity;
import cn.hamm.demo.module.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * <h1>Controller</h1>
 *
 * @author Hamm.cn
 */
@ApiController("oauth2")
@Slf4j
public class Oauth2Controller extends RootController implements IAppAction {
    private static final String APP_NOT_FOUND = "App(%s) not found!";
    private static final String REDIRECT_URI = "redirectUri";
    private static final String REDIRECT_URI_MISSING = "RedirectUri missing!";
    private static final String INVALID_APP_KEY = "Invalid appKey!";
    private static final String APP_KEY = "appKey";

    @Autowired
    private UserService userService;

    @Autowired
    private AppService appService;

    @Autowired
    private AppConfig appConfig;

    @GetMapping("authorize")
    public ModelAndView index(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        String appKey = request.getParameter(APP_KEY);
        if (!StringUtils.hasText(appKey)) {
            return showError(INVALID_APP_KEY);
        }
        AppEntity appEntity;
        try {
            appEntity = appService.getByAppKey(appKey);
        } catch (Exception exception) {
            return showError(String.format(APP_NOT_FOUND, appKey));
        }
        String redirectUri = request.getParameter(REDIRECT_URI);
        if (!StringUtils.hasText(redirectUri)) {
            return showError(REDIRECT_URI_MISSING);
        }
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies)) {
            // 没有cookie
            return redirectLogin(response, appKey, redirectUri);
        }
        String cookieString = null;
        for (Cookie c : cookies) {
            if (Configs.getCookieConfig().getAuthCookieName().equals(c.getName())) {
                cookieString = c.getValue();
                break;
            }
        }
        if (!StringUtils.hasText(cookieString)) {
            // 没有cookie
            return redirectLogin(response, appKey, redirectUri);
        }
        Long userId = userService.getUserIdByCookie(cookieString);
        if (Objects.isNull(userId)) {
            // cookie没有找到用户
            return redirectLogin(response, appKey, redirectUri);
        }
        UserEntity userEntity = userService.get(userId);
        String code = Utils.getRandomUtil().randomString();
        appEntity.setCode(code).setAppKey(appKey);
        userService.saveOauthCode(userEntity.getId(), appEntity);
        String redirectTarget = URLDecoder.decode(redirectUri, Charset.defaultCharset());
        String querySplit = "?";
        if (redirectTarget.contains(querySplit)) {
            redirectTarget += "&code=" + code;
        } else {
            redirectTarget += "?code=" + code;
        }
        redirect(response, redirectTarget);
        return null;
    }

    @Description("Code换取AccessToken")
    @Permission(login = false)
    @PostMapping("accessToken")
    public Json accessToken(@RequestBody @Validated(WhenCode2AccessToken.class) AppEntity appEntity) {
        String code = appEntity.getCode();
        Long userId = userService.getUserIdByOauthAppKeyAndCode(appEntity.getAppKey(), code);
        AppEntity existApp = appService.getByAppKey(appEntity.getAppKey());
        ServiceError.FORBIDDEN.whenNotEquals(existApp.getAppSecret(), appEntity.getAppSecret(), "应用秘钥错误");
        userService.removeOauthCode(existApp.getAppKey(), code);
        String accessToken = Utils.getSecurityUtil().createAccessToken(userId);
        return Json.data(accessToken);
    }

    /**
     * <h2>重定向到登录页面</h2>
     *
     * @param response    响应对象
     * @param appKey      AppKey
     * @param redirectUri 重定向地址
     * @return 无返回
     */
    private @Nullable ModelAndView redirectLogin(HttpServletResponse response, String appKey, String redirectUri) {
        String url = appConfig.getLoginUrl() +
                "?appKey=" +
                appKey +
                "&redirectUri=" +
                URLEncoder.encode(redirectUri, StandardCharsets.UTF_8);
        redirect(response, url);
        return null;
    }

    /**
     * <h2>显示一个错误页面</h2>
     *
     * @param error 错误信息
     * @return 错误页面
     */
    private @NotNull ModelAndView showError(String error) {
        ModelAndView view = new ModelAndView(Constant.ERROR);
        view.getModel().put(Constant.ERROR, error);
        return view;
    }

    /**
     * <h2>重定向到指定的URL</h2>
     *
     * @param response 响应体
     * @param url      目标URL
     */
    private void redirect(@NotNull HttpServletResponse response, String url) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}