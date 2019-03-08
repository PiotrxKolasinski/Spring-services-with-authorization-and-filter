package com.kolasinski.piotr.authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateBeanProvider {
    private final RestTemplateBuilder restTemplateBuilder;
    private final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    public RestTemplateBeanProvider(RestTemplateBuilder restTemplateBuilder, MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.mappingJackson2HttpMessageConverter = mappingJackson2HttpMessageConverter;
    }

    @Bean
    @Primary
    public RestTemplate provideRestTemplate() {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setOutputStreaming(false);
        BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory);
        RestTemplate restTemplate = restTemplateBuilder
                .additionalMessageConverters(mappingJackson2HttpMessageConverter)
                .build();
        restTemplate.setRequestFactory(bufferingClientHttpRequestFactory);
        return restTemplate;
    }
}
