package com.ioindustry.qboauth2.util;

import com.intuit.oauth2.client.OAuth2PlatformClient;
import com.intuit.oauth2.config.Environment;
import com.intuit.oauth2.config.OAuth2Config;
import org.springframework.stereotype.Component;

@Component
public class OAuthUtil {

    public OAuth2Config createOAuth2Config(String oAuth2AppClientId, String oAuth2AppClientSecret, Boolean production) {
        return new OAuth2Config.OAuth2ConfigBuilder(oAuth2AppClientId, oAuth2AppClientSecret).callDiscoveryAPI(production ? Environment.PRODUCTION : Environment.SANDBOX).buildConfig();
    }

    public OAuth2PlatformClient getOAuth2PlatformClient(String oAuth2AppClientId, String oAuth2AppClientSecret, Boolean production) {
        return new OAuth2PlatformClient(createOAuth2Config(oAuth2AppClientId, oAuth2AppClientSecret, production));
    }
}
