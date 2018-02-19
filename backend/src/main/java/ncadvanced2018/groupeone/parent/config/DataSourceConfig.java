package ncadvanced2018.groupeone.parent.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        String configFile;
        if (env.acceptsProfiles("prod")) {
            configFile = "/hikari-prod.properties";
        } else {
            configFile = "/hikari-dev.properties";
        }
        HikariConfig config = new HikariConfig(configFile);
        HikariDataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }
}