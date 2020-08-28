package com.saber.microservice.command.rest.config;

import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.distributed.jgroups.JGroupsConnector;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.serializer.xml.XStreamSerializer;
import org.jgroups.JChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import java.util.Collections;
@Configuration
@Slf4j
public class AppConfig {

    @Bean
    public XStreamSerializer xStreamSerializer() {
        return new XStreamSerializer();
    }

    @Bean
    @Qualifier(value = "distributedCommandGateway")
    public CommandGatewayFactoryBean<CommandGateway> distributedCommandGateway() {
        CommandGatewayFactoryBean<CommandGateway> distributedCommandGateway = new CommandGatewayFactoryBean<>();
        distributedCommandGateway.setCommandBus(distributedCommandBus());
        return distributedCommandGateway;
    }

    @Bean
    @Qualifier(value = "distributedCommandBus")
    public DistributedCommandBus distributedCommandBus(){
        return new DistributedCommandBus(jGroupsConnector());
    }
    @Bean
    public JGroupsConnector jGroupsConnector() {
        try {
            JChannel channel = new JChannel("udp_config.xml");
            String clusterName = "saber1366";
            JGroupsConnector jGroupsConnector = new JGroupsConnector(channel, clusterName, localSegment(), xStreamSerializer());
            jGroupsConnector.connect(100);
            return jGroupsConnector;
        } catch (Exception e) {
            log.error("Exception in jGroups  Cluster " + e.getMessage());
            return null;
        }
    }

    @Bean
    @Qualifier(value = "localCommandBus")
    public SimpleCommandBus localSegment() {
        SimpleCommandBus simpleCommandBus = new SimpleCommandBus();
        simpleCommandBus.setDispatchInterceptors(Collections.singletonList(new BeanValidationInterceptor()));
        return simpleCommandBus;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
