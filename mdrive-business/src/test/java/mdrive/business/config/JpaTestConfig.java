package mdrive.business.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


/**
 * Created by User on 12.01.14.
 */
@Configuration
@Import(JpaConfig.class)
public class JpaTestConfig {
}
