package cn.hamm.demo.common.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * <h1>应用配置文件</h1>
 *
 * @author Hamm.cn
 */
@Component
@Data
@Accessors(chain = true)
@Configuration
@ConfigurationProperties("app")
public class AppConfig {
    /**
     * <h3>登录地址 用于 {@code OAuth2}</h3>
     */
    private String loginUrl;

    /**
     * <h3>授权地址 用于 {@code OAuth2}</h3>
     */
    private String authorizeUrl;

    /**
     * <h2>默认房间ID {@code 不是房间号}</h2>
     */
    private long defaultRoomId = 1L;
}
