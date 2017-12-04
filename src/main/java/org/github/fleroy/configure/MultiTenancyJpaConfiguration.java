package org.github.fleroy.configure;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.github.fleroy.entity.Item;

import javax.sql.DataSource;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(JpaProperties.class)
public class MultiTenancyJpaConfiguration {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    @Autowired
    @Qualifier("dataSource2")
    private DataSource dataSource2;


    @Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private MultiTenantConnectionProvider multiTenantConnectionProvider;

	@Autowired
	private CurrentTenantIdentifierResolver currentTenantIdentifierResolver;

    @Bean
    @Qualifier("entityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder factoryBuilder) {
        Map<String, Object> vendorProperties = new LinkedHashMap<>();
        vendorProperties.putAll(jpaProperties.getHibernateProperties(dataSource));

        vendorProperties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
        vendorProperties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        vendorProperties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        vendorProperties.put("hibernate.search.default.indexBase", "/Users/fred/tmp");
        vendorProperties.put("hibernate.search.default.directory_provider", "filesystem");
        return factoryBuilder.dataSource(dataSource)
                .packages(Item.class.getPackage().getName())
                .properties(vendorProperties)
                .jta(false)
                .build();
    }

    @Bean
    @Qualifier("entityManagerFactory2")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory2(EntityManagerFactoryBuilder factoryBuilder) {
        Map<String, Object> vendorProperties = new LinkedHashMap<>();
        vendorProperties.putAll(jpaProperties.getHibernateProperties(dataSource));
        return factoryBuilder.dataSource(dataSource)
                .packages(Item.class.getPackage().getName())
                .properties(vendorProperties)
                .jta(false)
                .build();
    }

}
