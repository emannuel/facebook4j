package facebook4j;

import facebook4j.auth.AccessToken;
import org.junit.Before;

import java.io.InputStream;
import java.util.Properties;

public abstract class MockFacebookTestBase {

    protected MockFacebook facebook;

    @Before
    public void setUp() throws Exception {
        InputStream is = FacebookTestBase.class.getResourceAsStream("/test.properties");
        Properties p = new Properties();
        p.load(is);
        is.close();

        System.setProperty("facebook4j.debug", p.getProperty("debug") == null ? "true" : p.getProperty("debug"));
        System.setProperty("facebook4j.loggerFactory", p.getProperty("loggerFactory") == null ? "facebook4j.internal.logging.StdOutLoggerFactory" : p.getProperty("loggerFactory"));
        System.setProperty("facebook4j.http.prettyDebug", p.getProperty("http.prettyDebug") == null ? "true" : p.getProperty("http.prettyDebug"));
        System.setProperty("facebook4j.jsonStoreEnabled", p.getProperty("jsonStoreEnabled") == null ? "true" : p.getProperty("jsonStoreEnabled"));

        facebook = MockFacebookFactory.create();

        String appId = p.getProperty("oauth.appId");
        String appSecret = p.getProperty("oauth.appSecret");
        String accessToken = p.getProperty("oauth.accessToken");
        if (appId != null && appSecret != null) {
            facebook.setOAuthAppId(appId, appSecret);
            if (accessToken == null) {
                facebook.getOAuthAppAccessToken();
            }
        }
        if (accessToken != null) {
            facebook.setOAuthAccessToken(new AccessToken(accessToken));
        }
    }

}
