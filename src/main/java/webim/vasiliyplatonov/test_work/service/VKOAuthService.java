package webim.vasiliyplatonov.test_work.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
@ConfigurationProperties(prefix = "security.vk.oauth2")
public class VKOAuthService {

    private VkApiClient vkClient;
    private int clientId;
    private String clientSecret;
    private String host;
    private String port;
    private String scope;
    private String version;
    private boolean verify;
    private UserActor actor;

    public String getOAuthUrl() {
        return "https://oauth.vk.com/authorize?client_id=" + clientId + "&display=page&redirect_uri=" + host + "&scope=" + scope + "&response_type=code&v=" + version;
    }

    private void setVkClient() {
        this.vkClient = new VkApiClient(HttpTransportClient.getInstance());
    }

    public VkApiClient getVkApiClient() {
        if (vkClient == null) {
            this.setVkClient();
            return vkClient;
        } else return vkClient;
    }




}
