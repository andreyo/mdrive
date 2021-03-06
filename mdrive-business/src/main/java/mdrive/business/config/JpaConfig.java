package mdrive.business.config;

import mdrive.business.config.properties.JpaProperties;
import mdrive.business.bot.DriverBot;
import mdrive.business.bot.PassengerBot;
import mdrive.business.util.DBUnitDataExporter;
import mdrive.business.util.DBUnitDataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by User on 12.01.14.
 */
@Configuration
@ComponentScan("mdrive.business")
@Import(PropertiesConfig.class)
@EnableTransactionManagement
public class JpaConfig {

    @Autowired
    JpaProperties properties;

    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUrl(properties.getDbUrl());
        dataSource.setUsername(properties.getUserName());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setPackagesToScan(new String[]{"mdrive.business.model"});
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emfb.setJpaVendorAdapter(vendorAdapter);
        emfb.setJpaProperties(properties.getJpaProperties());
        return emfb;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }


    @Bean
    public DBUnitDataLoader dbUnitDataLoader(DataSource dataSource) throws Exception {
        DBUnitDataLoader dbUnitDataLoader = new DBUnitDataLoader(dataSource);
        dbUnitDataLoader.initTestDataCsv();
        return dbUnitDataLoader;
    }

    @Bean
    public DBUnitDataExporter dbUnitDataExporter() {
        return new DBUnitDataExporter();
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Bean
    public PassengerBot passengerBot() {
        return new PassengerBot();
    }

    @Bean
    public DriverBot driverBot() {
        return new DriverBot();
    }
}
