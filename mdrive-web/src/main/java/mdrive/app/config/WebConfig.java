package mdrive.app.config;

import mdrive.business.config.JpaConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by User on 18.01.14.
 */
@Configuration
@Import({JpaConfig.class})
public class WebConfig {

}
