package com.revature.firebaseSecurity.props;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ CorsConfigurationProps.class })
public class PropsConfiguration {
}
