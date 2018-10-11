package webim.vasiliyplatonov.test_work.controller;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.friends.responses.GetFieldsResponse;
import com.vk.api.sdk.queries.users.UserField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import webim.vasiliyplatonov.test_work.service.VKOAuthService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MainController {

    @Value("${vk.countOfFriends}")
    int countOfFriends;

    @Autowired
    private VKOAuthService vkOAuth;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String index(@CookieValue(value = "authorized", required = false) Cookie authorized) {
        if (authorized != null && authorized.getValue().equals("true"))
            return "redirect:main";
        else
            return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:" + vkOAuth.getOAuthUrl();
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("authorized")) {
                cookie.setMaxAge(0);
                cookie.setValue(null);
                response.addCookie(cookie);
            }
        }

        return "redirect:index";
    }

    @GetMapping("/verify")
    public String verify(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");

        if (code != null) {
            vkOAuth.setUserAuthResponse(code);
            Cookie authorized = new Cookie("authorized", "true");
            authorized.setMaxAge(60 * 60 * 4); // four hours
            response.addCookie(authorized);
            return "redirect:main";
        }

        return "index";
    }

    @GetMapping("/main")
    public String main(Model model,
                       @CookieValue(value = "authorized", required = false) Cookie authorized) {

        if (authorized != null && authorized.getValue().equals("true")) {

            GetFieldsResponse friends = null;
            try {
                friends = vkOAuth.getVkApiClient()
                        .friends()
                        .get(vkOAuth.getActor(), UserField.COUNTRY)
                        .count(countOfFriends)
                        .execute();
            } catch (ApiException | ClientException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
                model.addAttribute("error", "An error occurred while trying to log in. Try to remove cookies. Or just try to authorize again");
                return "index";
            }

            model.addAttribute("friends", friends.getItems());
            return "main";
        } else
            return "index";
    }
}


