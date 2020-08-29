package com.saber.microservice.handle.event.core.config;

import org.axonframework.eventhandling.*;
import org.axonframework.eventhandling.amqp.DefaultAMQPMessageConverter;
import org.axonframework.eventhandling.amqp.spring.ListenerContainerLifecycleManager;
import org.axonframework.eventhandling.amqp.spring.SpringAMQPTerminal;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.serializer.xml.XStreamSerializer;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${ecom.amqp.rabbit.address}")
    private String rabbitMQAddress;

    @Value("${ecom.amqp.rabbit.username}")
    private String rabbitMQUser;

    @Value("${ecom.amqp.rabbit.password}")
    private String rabbitMQPassword;

    @Value("${ecom.amqp.rabbit.vhost}")
    private String rabbitMQVhost;

    @Value("${ecom.amqp.rabbit.exchange}")
    private String rabbitMQExchange;

    @Value("${ecom.amqp.rabbit.queue}")
    private String rabbitMQQueue;

    @Bean
    public XStreamSerializer xStreamSerializer() {
        return new XStreamSerializer();
    }

    @Bean
    public SimpleCluster simpleCluster() {
        return new SimpleCluster(this.rabbitMQQueue);
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(this.rabbitMQAddress);
        connectionFactory.setVirtualHost(this.rabbitMQVhost);
        connectionFactory.setUsername(this.rabbitMQUser);
        connectionFactory.setPassword(this.rabbitMQPassword);
        connectionFactory.setConnectionTimeout(5000);
        connectionFactory.setRequestedHeartBeat(20);
        return connectionFactory;
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(this.rabbitMQExchange, true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(this.rabbitMQQueue, true, false, false);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(fanoutExchange());
    }

    @Bean
    public DefaultAMQPMessageConverter messageConverter() {
        return new DefaultAMQPMessageConverter(xStreamSerializer());
    }

    @Bean
    public ListenerContainerLifecycleManager listenerContainerLifecycleManager() {
        ListenerContainerLifecycleManager listenerContainerLifecycleManager = new ListenerContainerLifecycleManager();
        listenerContainerLifecycleManager.setConnectionFactory(connectionFactory());
        return listenerContainerLifecycleManager;
    }
    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor(){
        AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor =
                                                    new AnnotationEventListenerBeanPostProcessor();

        annotationEventListenerBeanPostProcessor.setEventBus(eventBus());
        return annotationEventListenerBeanPostProcessor;
    }
    @Bean
    public EventBusTerminal eventBusTerminal(){
        SpringAMQPTerminal springAMQPTerminal = new SpringAMQPTerminal();
        springAMQPTerminal.setListenerContainerLifecycleManager(listenerContainerLifecycleManager());
        springAMQPTerminal.setSerializer(xStreamSerializer());
        springAMQPTerminal.setConnectionFactory(connectionFactory());
        springAMQPTerminal.setExchangeName(this.rabbitMQExchange);
        springAMQPTerminal.setTransactional(true);
        springAMQPTerminal.setDurable(true);

        return springAMQPTerminal;
    }

    @Bean
    public EventBus eventBus() {
      return new ClusteringEventBus(new DefaultClusterSelector(simpleCluster()),eventBusTerminal());
     }

}
