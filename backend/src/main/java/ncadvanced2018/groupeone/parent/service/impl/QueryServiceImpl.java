package ncadvanced2018.groupeone.parent.service.impl;

import ncadvanced2018.groupeone.parent.service.QueryService;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@PropertySource("classpath:queries.properties")
public class QueryServiceImpl implements QueryService {

    @Resource
    private Environment env;

    @Override
    public String getQuery(String name) {
        return this.env.getRequiredProperty(name);
    }
}
