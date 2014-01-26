package mdrive.app.config;

import mdrive.business.config.JpaConfig;
import mdrive.business.service.DBUnitDataLoader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by User on 18.01.14.
 */
@Configuration
@Import({JpaConfig.class})
public class WebConfig {

    @Autowired
    DBUnitDataLoader dbUnitDataLoader;


    @Bean
    public InitializingBean init() {
        return new InitializingBean() {
            @Override
            public void afterPropertiesSet() throws Exception {
                dbUnitDataLoader.initTestDataCsv();
            }
        };
    }

}
