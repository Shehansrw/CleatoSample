package com.authapi.demo.configurations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Configuration
public class MessageConfiguration {

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource() {
        final ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
        res.setDefaultEncoding("UTF-8");
        res.addBasenames("classpath:messages/validation_en");
        return res;
    }


    @Bean
    public MessageSourceAccessor getMessageSourceAccessor(final MessageSource messageSource) {
        return new MessageSourceAccessor(messageSource, Locale.US);
    }
}
