package com.ssafy.config;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // configuration.addAllowedOrigin("*");
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        //configuration.addExposedHeader(JwtTokenUtil.HEADER_STRING);
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//    		registry.addResourceHandler("/resources/**")
//    				.addResourceLocations("/WEB-INF/resources/");
//    		
//    		registry.addResourceHandler("swagger-ui.html")
//    				.addResourceLocations("classpath:/META-INF/resources/");
//
//    		registry.addResourceHandler("/webjars/**")
//    				.addResourceLocations("classpath:/META-INF/resources/webjars/");
    		
    		/*
    		 * 
    		 * Front-end에서 참조하는 URL을 /dist로 매핑
    		 * 
    		 */
            registry.addResourceHandler("/css/**")
        			.addResourceLocations("classpath:/static/");
        	registry.addResourceHandler("/fonts/**")
        			.addResourceLocations("classpath:/static/");
            registry.addResourceHandler("/icons/**")
				    .addResourceLocations("classpath:/static/");
            registry.addResourceHandler("/img/**")
			        .addResourceLocations("classpath:/static/");
            registry.addResourceHandler("/js/**")
				    .addResourceLocations("classpath:/static/");
    }

    public Filter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeHeaders(true);
        loggingFilter.setMaxPayloadLength(64000);
        return loggingFilter;
    }

    @Bean
    public FilterRegistrationBean loggingFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(requestLoggingFilter());
        registration.addUrlPatterns("/api/*");
        return registration;
    }
    
    // 8081에서 오는 요청은 허용
    @Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedHeaders("*")
		.allowedOrigins("http://localhost:8080", "https://localhost:8080")
		.allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "PATCH");
	}

}
