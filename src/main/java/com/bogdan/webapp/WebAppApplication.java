package com.bogdan.webapp;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@ConfigurationPropertiesScan("com.bogdan.webapp")
public class WebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAppApplication.class, args);
    }

//    @Bean
//    public FilterRegistrationBean<AuthorizationFilter> logFilter() {
//        FilterRegistrationBean<AuthorizationFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new AuthorizationFilter());
//        registrationBean.addUrlPatterns("/students/login");
//        return registrationBean;
//    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


}
