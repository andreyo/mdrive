package mdrive.oauth;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.GoogleApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.builder.api.VkontakteApi;
import org.scribe.builder.api.YahooApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

/**
 * Created by IntelliJ IDEA.
 * User: Elena
 * Date: 28.03.12
 * Time: 8:51
 * To change this template use File | Settings | File Templates.
 */
public class OAuthServiceFactory {

    public static final Token EMPTY_TOKEN = null;

    private static final String GOOGLE_SCOPE = "https://docs.google.com/feeds/";

    private static OAuthService googleOAuthService;
    private static OAuthService yahooOAuthService;
    private static OAuthService twitterOAuthService;
    private static OAuthService facebookOAuthService;
    private static OAuthService vkontakteOAuthService;

    public static synchronized OAuthService getFacebookOAuthService(String callBackUrl) {
        if (facebookOAuthService == null) {
            facebookOAuthService = new ServiceBuilder().provider(FacebookApi.class).apiKey("noidea").apiSecret("noidea")
                    .callback(callBackUrl).build();
        }
        return facebookOAuthService;
    }

    public static synchronized OAuthService getGoogleOAuthService(String callBackUrl) {
        if (googleOAuthService == null) {
            googleOAuthService = new ServiceBuilder().provider(GoogleApi.class).apiKey("anonymous")
                    .apiSecret("anonymous").scope(GOOGLE_SCOPE).callback(callBackUrl).build();
        }
        return googleOAuthService;
    }

    public static synchronized OAuthService getTwitterOAuthService(String callBackUrl) {
        if (twitterOAuthService == null) {
            twitterOAuthService = new ServiceBuilder().provider(TwitterApi.class).apiKey("YdFyyh9KNXmUoJKKyiFrjw")
                    .apiSecret("25rgbvSE4xUPVTaSEu6vwPDbjTtkqnWdOLOP3TXDq4").callback(callBackUrl).build();
        }
        return twitterOAuthService;
    }

    public static synchronized OAuthService getVkontakteOAuthService(String callBackUrl) {
        if (vkontakteOAuthService == null) {
            vkontakteOAuthService = new ServiceBuilder().provider(VkontakteApi.class).apiKey("2880292")
                    .apiSecret("Lkf6xVAYHcf6YSOzoWRc").scope("friends,wall,offline").callback(callBackUrl).build();
        }
        return vkontakteOAuthService;
    }

    public static synchronized OAuthService getYahooOAuthService(String callBackUrl) {
        if (yahooOAuthService == null) {
            yahooOAuthService = new ServiceBuilder().provider(YahooApi.class)
                    .apiKey("dj0yJmk9TUV0c0RaV1M5QmRPJmQ9WVdrOVdWZFNSMEpOTldNbWNHbzlNelk0TlRjMk5qWXkmcz1jb25zdW1lcnNlY3JldCZ4PWI5")
                    .apiSecret("b34d83dab9af0c0df1af03d721d5818256c9d6ed").callback(callBackUrl).build();
        }
        return yahooOAuthService;
    }
}
