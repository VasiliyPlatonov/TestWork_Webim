package webim.vasiliyplatonov.test_work.controller;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.friends.responses.GetFieldsResponse;
import com.vk.api.sdk.queries.users.UserField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import webim.vasiliyplatonov.test_work.service.VKOAuthService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    @Autowired
    private VKOAuthService vkOAuth;

    @GetMapping("/{login}")
    public String vkAuth() {
        return "redirect:" + vkOAuth.getOAuthUrl();
    }

    @GetMapping("/")
    public String index(HttpServletRequest request) {

        String code = (String) request.getParameter("code");
        UserActor actor = null;
        VkApiClient vk = vkOAuth.getVkApiClient();

        if (code != null) {
            try {
                UserAuthResponse authResponse = vk.oauth()
                        .userAuthorizationCodeFlow(vkOAuth.getClientId(), vkOAuth.getClientSecret(), vkOAuth.getHost(), code)
                        .execute();
                actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            } catch (OAuthException e) {
                e.getRedirectUri();
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            }

        }

        if (actor != null) {
            try {
                GetFieldsResponse friends = vk.friends().get(actor, UserField.COUNTRY).count(5).execute();
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            }
        }

        return "index";
    }
}
