//package com.jobms.Job_Micro.job;
//
//import org.springframework.cloud.client.loadbalancer.LoadBalanced;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.client.RestTemplate;
//
//@Configuration
//public class AppConfig {
//    @Bean
//    @LoadBalanced // Required if using Eureka service names like http://COMPANY-SERVICE/
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//}
