package mdrive.business.config.properties;

import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

/**
 * Created by User on 12.01.14.
 */
public class JpaProperties {

    @Value("${driver.className}")
    private String driverClassName;

    @Value("${db.url}")
    private String dbUrl;

    @Value("${db.userName}")
    private String userName;

    @Value("${db.password}")
    private String password;

    public String getDriverClassName() {
        return driverClassName;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Properties getJpaProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "create");
                setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
                setProperty("hibernate.ejb.naming_strategy", "org.hibernate.cfg.DefaultComponentSafeNamingStrategy");
            }
        };
    }
}
