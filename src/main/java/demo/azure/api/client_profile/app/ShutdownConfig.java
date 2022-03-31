package demo.azure.api.client_profile.app;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ShutdownConfig {

    @Bean
    public TerminateBean getTerminateBean() {
        return new TerminateBean();
    }
}
