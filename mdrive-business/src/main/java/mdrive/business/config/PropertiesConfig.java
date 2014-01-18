package mdrive.business.config;

import mdrive.business.config.properties.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by User on 12.01.14.
 */
@Configuration
@PropertySource("classpath:/jpa.properties")
public class PropertiesConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JpaProperties jpaProperties() {
        return new JpaProperties();
    }
}
