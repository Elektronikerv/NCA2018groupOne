package ncadvanced2018.groupeone.parent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;


@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class DaoConfig {

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.driverClassName}")
    String driverClassName;

    @Value("${spring.datasource.url}")
    String url;


    @Bean
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setUrl(url);

        return dataSource;
    }
}
