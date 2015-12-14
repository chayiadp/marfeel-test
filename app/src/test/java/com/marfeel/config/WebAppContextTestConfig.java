package com.marfeel.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.marfeel.app.repository.SiteRepository;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "com.marfeel.app.controllers",
        "com.marfeel.app.services",
        "com.marfeel.app.beans"
})
public class WebAppContextTestConfig extends WebMvcConfigurerAdapter {


    @Bean
    public SiteRepository siteRepository() {
        return Mockito.mock(SiteRepository.class);
    }
    
}
