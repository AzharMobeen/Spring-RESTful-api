package com.az.spring.config;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.az.spring")
@EnableJpaRepositories("com.az.spring.repositories")
@EnableWebMvc
@EnableTransactionManagement
public class AppConfig {

    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String PROPERTY_NAME_HIBERNATE_PACKAGES_TO_SCAN = "hibernate.packages.to.scan";
    private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    @Resource
    private Environment env;


   /*@Bean
   public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(false)
                .setName("testdb")
                .setType(EmbeddedDatabaseType.H2)
                .addDefaultScripts()
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .build();
    }*/
    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
    }

    /**
     * Load hibernate properties from environment variable.
     *
     * @return Properties settings of hibernate configuration
     */
    private Properties loadHibernateProperties() {
        Properties properties = new Properties();
        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
        properties.put(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
        return properties;
    }

    /**
     * make necessary settings available throughout the application that will be
     * used in application. jpaSettings, datasource
     *
     * @return LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
     *         variable
     * @throws NamingException
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        final DataSource ds = dataSource();
        entityManagerFactoryBean.setDataSource(ds);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_PACKAGES_TO_SCAN));
        //entityManagerFactoryBean.setPackagesToScan("com.az.spring.entities");
        entityManagerFactoryBean.setJpaProperties(loadHibernateProperties());
        return entityManagerFactoryBean;
    }

    /**
     * make necessary settings of transaction manager throughout the application that will be
     * used in database transaction.
     *
     * @return JpaTransactionManager transactionManager
     *         variable
     */
    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}
