package mdrive.config;

import mdrive.app.MApplication;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.util.tester.WicketTester;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by User on 26.01.14.
 */
@Configuration
public class TestWebConfig {

    @Bean
    public WebApplication webApplication() {
        return new MApplication();
    }

//    @Bean
//    public ApplicationContext applicationContext(MApplication mApplication) {
//        return mApplication.getSpringContext();
//    }

    @Bean
    public WicketTester wicketTester(WebApplication webApplication) {
        return new WicketTester(webApplication);
    }
}
