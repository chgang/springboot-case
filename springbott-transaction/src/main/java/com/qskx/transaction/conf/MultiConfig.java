package com.qskx.transaction.conf;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"com.qskx.transaction.dao"}, sqlSessionFactoryRef = "userSqlSessionFactory")
public class MultiConfig {

    @Bean(name = "userDataSource")
    @ConfigurationProperties(prefix = "user.datasource")
    @Primary
    public DataSource userDataSource() {
        //指定使用DruidDataSource
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

    @Bean(name = "userSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("userDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(typeAliasPackage);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources(mapperLocations));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("userDataSource") DataSource dataSource) {
        DataSourceTransactionManager userTm = new DataSourceTransactionManager(userDataSource());
        DataSourceTransactionManager orderTm = new DataSourceTransactionManager(orderDataSource());
        ChainedTransactionManager chainedTransactionManager = new ChainedTransactionManager(userTm, orderTm);
        return chainedTransactionManager;
    }

    @Primary
    @Bean(name = "userSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("userSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Value("${user.datasource.typeAliasPackage}")
    private String typeAliasPackage;

    @Value("${user.datasource.mapperLocations}")
    private String mapperLocations;

    @Bean(name = "orderDataSource")
    @ConfigurationProperties(prefix = "order.datasource")
    public DataSource orderDataSource() {
        //指定使用DruidDataSource
        return DataSourceBuilder.create().type(com.alibaba.druid.pool.DruidDataSource.class).build();
    }

    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(@Qualifier("orderDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(orderTypeAliasPackage);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources(orderMapperLocations));
        return bean.getObject();
    }

    @Bean(name = "orderSqlSessionTemplate")
    public SqlSessionTemplate OrderSqlSessionTemplate(@Qualifier("orderSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Value("${order.datasource.typeAliasPackage}")
    private String orderTypeAliasPackage;

    @Value("${order.datasource.mapperLocations}")
    private String orderMapperLocations;
}
