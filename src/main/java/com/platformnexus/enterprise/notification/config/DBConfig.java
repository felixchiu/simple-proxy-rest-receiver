package com.platformnexus.enterprise.notification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
/**
 * Created by Felix Chiu on 2/23/18.
 */
@Configuration
@EnableJpaRepositories(
        basePackages = "com.platformnexus.enterprise.notification.data.repository.notification",
        entityManagerFactoryRef = "notificationEntityManager",
        transactionManagerRef = "notificationTransactionManager"
)
public class DBConfig {

    @Autowired
    private Environment env;


    @Bean
    public LocalContainerEntityManagerFactoryBean notificationEntityManager() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(notificationDataSource());
        em.setPackagesToScan(
                new String[] { "com.platformnexus.enterprise.notification.data.entity.notification" });

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("notification.jdbc.hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("notification.jdbc.hibernate.dialect"));
        properties.put("hibernate.dbcp.testWhileIdle", env.getProperty("notification.jdbc.hibernate.dbcp.testWhileIdle"));
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource notificationDataSource() {

        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("notification.jdbc.driverClassName"));
        dataSource.setUrl(env.getProperty("notification.jdbc.url"));
        dataSource.setUsername(env.getProperty("notification.jdbc.username"));
        dataSource.setPassword(env.getProperty("notification.jdbc.password"));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager notificationTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                notificationEntityManager().getObject());
        return transactionManager;
    }
    
}
