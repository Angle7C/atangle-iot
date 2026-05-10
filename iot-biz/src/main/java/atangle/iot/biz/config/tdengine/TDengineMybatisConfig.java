package atangle.iot.biz.config.tdengine;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(prefix = "tdengine.datasource", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(TDengineDataSourceProperties.class)
@MapperScan(
        basePackages = "atangle.iot.biz.tdengine.mapper",
        sqlSessionFactoryRef = "tdengineSqlSessionFactory",
        sqlSessionTemplateRef = "tdengineSqlSessionTemplate"
)
public class TDengineMybatisConfig {

    @Bean
    public DataSource tdengineDataSource(TDengineDataSourceProperties properties) {
        org.springframework.jdbc.datasource.DriverManagerDataSource dataSource =
                new org.springframework.jdbc.datasource.DriverManagerDataSource();
        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setUrl(properties.getUrl());
        dataSource.setUsername(properties.getUsername());
        dataSource.setPassword(properties.getPassword());
        return dataSource;
    }

    @Bean
    public SqlSessionFactory tdengineSqlSessionFactory(DataSource tdengineDataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(tdengineDataSource);
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate tdengineSqlSessionTemplate(SqlSessionFactory tdengineSqlSessionFactory) {
        return new SqlSessionTemplate(tdengineSqlSessionFactory);
    }

    @Bean
    public DataSourceTransactionManager tdengineTransactionManager(DataSource tdengineDataSource) {
        return new DataSourceTransactionManager(tdengineDataSource);
    }
}
