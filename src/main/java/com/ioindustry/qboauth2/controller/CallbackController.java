package com.ioindustry.qboauth2.controller;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.data.BearerTokenResponse;
import com.intuit.oauth2.exception.OAuthException;
import com.ioindustry.qboauth2.util.OAuthUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class CallbackController {

    private static final Logger logger = Logger.getLogger(CallbackController.class);

    private final OAuthUtil oAuthUtil;

    public CallbackController(OAuthUtil oAuthUtil) {
        this.oAuthUtil = oAuthUtil;
    }

    @RequestMapping("/oauth2redirect")
    public String callBackFromOAuth(@RequestParam("code") String authCode, @RequestParam("state") String state, @RequestParam(value = "realmId", required = false) String realmId, HttpSession session, Model model) {
        logger.info("inside oauth2redirect");
        try {
            String csrfToken = (String) session.getAttribute("csrfToken");
            if (csrfToken != null && csrfToken.equals(state)) {
                OAuth2PlatformClient client = oAuthUtil.getOAuth2PlatformClient((String) session.getAttribute("oAuth2AppClientId"), (String) session.getAttribute("oAuth2AppClientSecret"), (Boolean) session.getAttribute("production"));
                String redirectUri = (String) session.getAttribute("oAuth2AppRedirectUri");
                logger.info("inside oauth2redirect -- redirectUri " + redirectUri);

                BearerTokenResponse bearerTokenResponse = client.retrieveBearerTokens(authCode, redirectUri);

                model.addAttribute("realmId", realmId);
                model.addAttribute("auth_code", authCode);
                model.addAttribute("access_token", bearerTokenResponse.getAccessToken());
                model.addAttribute("refresh_token", bearerTokenResponse.getRefreshToken());

                return "connected";
            }
            logger.info("csrf token mismatch ");
        } catch (OAuthException e) {
            logger.error("Exception in callback handler ", e);
        }
        return null;
    }
}
