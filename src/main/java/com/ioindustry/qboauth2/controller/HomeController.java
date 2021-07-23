package com.ioindustry.qboauth2.controller;

import com.intuit.oauth2.config.OAuth2Config;
import com.intuit.oauth2.config.Scope;
import com.intuit.oauth2.exception.InvalidRequestException;
import com.ioindustry.qboauth2.util.OAuthUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    private static final Logger logger = Logger.getLogger(HomeController.class);

    private final OAuthUtil oAuthUtil;

    public HomeController(OAuthUtil oAuthUtil) {
        this.oAuthUtil = oAuthUtil;
    }

    @RequestMapping("/")
    public String home(Model model) {
        model.addAttribute("oAuth2AppClientId", "");
        model.addAttribute("oAuth2AppClientSecret", "");
        model.addAttribute("production", Boolean.FALSE);
        model.addAttribute("oAuth2AppRedirectUri", "http://localhost:8080/oauth2redirect");

        return "home";
    }

    @RequestMapping("/connected")
    public String connected() {
        return "connected";
    }

    @RequestMapping("/connectToQuickbooks")
    public View connectToQuickbooks(HttpSession session, HttpServletRequest request) {
        logger.info("inside connectToQuickbooks ");

        session.setAttribute("oAuth2AppClientId", request.getParameter("oAuth2AppClientId"));
        session.setAttribute("oAuth2AppClientSecret", request.getParameter("oAuth2AppClientSecret"));
        session.setAttribute("production", request.getParameter("production") != null);
        session.setAttribute("oAuth2AppRedirectUri", request.getParameter("oAuth2AppRedirectUri"));

        OAuth2Config oauth2Config = oAuthUtil.createOAuth2Config((String) session.getAttribute("oAuth2AppClientId"), (String) session.getAttribute("oAuth2AppClientSecret"), (Boolean) session.getAttribute("production"));

        String redirectUri = (String) session.getAttribute("oAuth2AppRedirectUri");

        String csrf = oauth2Config.generateCSRFToken();
        session.setAttribute("csrfToken", csrf);
        try {
            List<Scope> scopes = new ArrayList<>();
            scopes.add(Scope.Accounting);
            System.out.println(scopes);
            System.out.println(redirectUri);
            System.out.println(csrf);
            return new RedirectView(oauth2Config.prepareUrl(scopes, redirectUri, csrf), true, true, false);
        } catch (InvalidRequestException e) {
            logger.error("Exception calling connectToQuickbooks ", e);
        }
        return null;
    }
}
