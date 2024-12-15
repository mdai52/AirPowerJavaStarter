package cn.hamm.demo.module.open.oauth.request;

import cn.hamm.demo.module.open.oauth.OauthAppKeyRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <h1>创建Code请求</h1>
 *
 * @author Hamm
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class OauthCreateCodeRequest extends OauthAppKeyRequest {

    @NotBlank(groups = {WhenCreateCode.class})
    private String scope;

    public interface WhenCreateCode {
    }
}
