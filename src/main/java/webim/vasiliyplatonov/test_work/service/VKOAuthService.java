package webim.vasiliyplatonov.test_work.service;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@ConfigurationProperties(prefix = "security.vk.oauth2")
public class VKOAuthService {

    private VkApiClient vkApiClient;
    private int clientId;
    private String clientSecret;
    private String redirectUri;
    private String port;
    private String scope;
    private String version;
    private UserActor actor;
    private UserAuthResponse userAuthResponse;


    public String getOAuthUrl() {
        return "https://oauth.vk.com/authorize?client_id=" + clientId + "&display=page&redirect_uri=" + redirectUri + "&scope=" + scope + "&response_type=code&v=" + version;
    }

    @PostConstruct
    private void customInit() {
        setVkApiClient();
    }

    private void setVkApiClient() {
        this.vkApiClient = new VkApiClient(HttpTransportClient.getInstance());
    }

    public VkApiClient getVkApiClient() {
        return vkApiClient;
    }

    public void setActor() {
        actor = new UserActor(userAuthResponse.getUserId(), userAuthResponse.getAccessToken());
    }

    public UserActor getActor() {
        if (actor == null) {
            setActor();
            return actor;
        } else
            return actor;
    }


    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public UserAuthResponse getUserAuthResponse() {
        return userAuthResponse;
    }

    public void setUserAuthResponse(String code) {
        try {
            userAuthResponse = this.vkApiClient.oauth()
                    .userAuthorizationCodeFlow(clientId, clientSecret, redirectUri, code)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

}

