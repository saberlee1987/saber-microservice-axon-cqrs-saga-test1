package com.saber.rmicroservice.commandand.event.config;

import com.mongodb.Mongo;
import com.saber.rmicroservice.commandand.event.models.order.OrderEntity;
import com.saber.rmicroservice.commandand.event.models.product.ProductEntity;
import com.saber.rmicroservice.commandand.event.sagas.OrderProcessSaga;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.distributed.jgroups.JGroupsConnector;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.common.jpa.SimpleEntityManagerProvider;
import org.axonframework.eventhandling.*;
import org.axonframework.eventhandling.amqp.DefaultAMQPMessageConverter;
import org.axonframework.eventhandling.amqp.spring.ListenerContainerLifecycleManager;
import org.axonframework.eventhandling.amqp.spring.SpringAMQPTerminal;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.repository.GenericJpaRepository;
import org.axonframework.saga.GenericSagaFactory;
import org.axonframework.saga.SagaManager;
import org.axonframework.saga.SagaRepository;
import org.axonframework.saga.annotation.AnnotatedSagaManager;
import org.axonframework.saga.repository.mongo.DefaultMongoTemplate;
import org.axonframework.saga.repository.mongo.MongoSagaRepository;
import org.axonframework.saga.repository.mongo.MongoTemplate;
import org.axonframework.saga.spring.SpringResourceInjector;
import org.axonframework.serializer.xml.XStreamSerializer;
import org.axonframework.unitofwork.SpringTransactionManager;
import org.jgroups.JChannel;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;


@Configuration
@Slf4j
public class AppConfig {

    @PersistenceContext
    private EntityManager entityManager;
    @Qualifier(value = "transactionManager")
    @Autowired
    private  PlatformTransactionManager transactionManager;

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

    @Autowired
    private Mongo mongo;


    @Bean
    public XStreamSerializer xStreamSerializer() {
        return new XStreamSerializer();
    }

    @Bean
    @Qualifier(value = "localSegment")
    public SimpleCommandBus localSegment() {
        SimpleCommandBus simpleCommandBus = new SimpleCommandBus();

        SpringTransactionManager springTransactionManager = new SpringTransactionManager(transactionManager);

        simpleCommandBus.setTransactionManager(springTransactionManager);
        simpleCommandBus.setDispatchInterceptors(Collections.singletonList(new BeanValidationInterceptor()));

        return simpleCommandBus;
    }

    @Bean
    public JGroupsConnector jGroupsConnector() {
        try {
            JChannel channel = new JChannel("udp_config.xml");
            String clusterName = "saber1366";
            JGroupsConnector jGroupsConnector = new JGroupsConnector(channel, clusterName,
                    localSegment(), xStreamSerializer());
            jGroupsConnector.connect(100);
            return jGroupsConnector;
        } catch (Exception ex) {
            log.error("Can not Connect To jGroupsConnector ===> " + ex.getMessage());
            return null;
        }
    }

    @Bean
    @Qualifier(value = "distributedCommandBus")
    public DistributedCommandBus distributedCommandBus() {
        return new DistributedCommandBus(jGroupsConnector());
    }

    @Bean
    @Qualifier(value = "distributedCommandGateway")
    public CommandGatewayFactoryBean<CommandGateway> distributedCommandGateway() {
        CommandGatewayFactoryBean<CommandGateway> commandGatewayFactoryBean = new CommandGatewayFactoryBean<>();
        commandGatewayFactoryBean.setCommandBus(distributedCommandBus());
        return commandGatewayFactoryBean;
    }

    @Bean
    @Qualifier(value = "localCommandGetWay")
    public CommandGatewayFactoryBean<CommandGateway> localCommandGetWayFactoryBean() {
        CommandGatewayFactoryBean<CommandGateway> localCommandGetWay = new CommandGatewayFactoryBean<>();
        localCommandGetWay.setCommandBus(localSegment());
        return localCommandGetWay;
    }


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(this.rabbitMQAddress);
        connectionFactory.setUsername(this.rabbitMQUser);
        connectionFactory.setPassword(this.rabbitMQPassword);
        connectionFactory.setVirtualHost(this.rabbitMQVhost);
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
    public DefaultAMQPMessageConverter defaultAMQPMessageConverter() {
        return new DefaultAMQPMessageConverter(xStreamSerializer());
    }

    @Bean
    public ListenerContainerLifecycleManager listenerContainerLifecycleManager() {
        ListenerContainerLifecycleManager listenerContainerLifecycleManager = new ListenerContainerLifecycleManager();
        listenerContainerLifecycleManager.setConnectionFactory(connectionFactory());
        return listenerContainerLifecycleManager;
    }

    @Bean
    public EventBusTerminal eventBusTerminal() {
        SpringAMQPTerminal terminal = new SpringAMQPTerminal();
        terminal.setSerializer(xStreamSerializer());
        terminal.setConnectionFactory(connectionFactory());
        terminal.setExchangeName(this.rabbitMQExchange);
        terminal.setListenerContainerLifecycleManager(listenerContainerLifecycleManager());
        terminal.setDurable(true);
        terminal.setTransactional(true);
        return terminal;
    }

    @Bean
    public SimpleCluster simpleCluster() {
        return new SimpleCluster(this.rabbitMQQueue);
    }


    @Bean
    public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
        AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor =
                new AnnotationEventListenerBeanPostProcessor();
        annotationEventListenerBeanPostProcessor.setEventBus(eventBus());
        return annotationEventListenerBeanPostProcessor;
    }

    @Bean
    public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
        AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor =
                new AnnotationCommandHandlerBeanPostProcessor();
        annotationCommandHandlerBeanPostProcessor.setCommandBus(distributedCommandBus());
        return annotationCommandHandlerBeanPostProcessor;
    }


    @Bean
    public EventBus eventBus() {
        ClusteringEventBus eventBus =
                new ClusteringEventBus(new DefaultClusterSelector(simpleCluster()),eventBusTerminal());
        eventBus.subscribe(sagaManager());
        return eventBus;
    }

    @Bean
    public SpringResourceInjector springResourceInjector() {
        return new SpringResourceInjector();
    }


    @Bean
    public MongoTemplate mongoTemplateAxon() {
        return new DefaultMongoTemplate(this.mongo);
    }

    @Bean(name = "sagaRepository")
    public SagaRepository sagaRepository() {
        MongoSagaRepository sagaRepository = new MongoSagaRepository(mongoTemplateAxon());
        sagaRepository.setResourceInjector(springResourceInjector());
        return sagaRepository;
    }

    @Bean(name = "sagaManager")
    @SuppressWarnings("All")
    public SagaManager sagaManager() {
        GenericSagaFactory sagaFactory = new GenericSagaFactory();
        sagaFactory.setResourceInjector(springResourceInjector());

        AnnotatedSagaManager sagaManager = new AnnotatedSagaManager(sagaRepository(),
                sagaFactory, OrderProcessSaga.class);

        sagaManager.setSynchronizeSagaAccess(false);
        sagaManager.setSuppressExceptions(false);
        return sagaManager;
    }


    @Bean
    @Qualifier(value = "productRepository")
    public GenericJpaRepository<ProductEntity> productEntityGenericJpaRepository() {

        SimpleEntityManagerProvider entityManagerProvider = new SimpleEntityManagerProvider(this.entityManager);

        GenericJpaRepository<ProductEntity> productEntityGenericJpaRepository =
                                  new GenericJpaRepository<>(entityManagerProvider, ProductEntity.class);

        productEntityGenericJpaRepository.setEventBus(eventBus());

        return productEntityGenericJpaRepository;

    }
    @Bean
    @Qualifier(value = "orderRepository")
    public GenericJpaRepository<OrderEntity> orderEntityGenericJpaRepository(){
        SimpleEntityManagerProvider entityManagerProvider = new SimpleEntityManagerProvider(this.entityManager);

        GenericJpaRepository<OrderEntity> orderEntityGenericJpaRepository =
                           new GenericJpaRepository<>(entityManagerProvider,OrderEntity.class);

        orderEntityGenericJpaRepository.setEventBus(eventBus());

        return orderEntityGenericJpaRepository;
    }

}
