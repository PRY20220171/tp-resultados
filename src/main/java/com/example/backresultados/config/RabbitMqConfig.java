package com.example.backresultados.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@EnableRabbit
public class RabbitMqConfig {
    @Autowired
    private Environment environment;
    @Bean
    public ConnectionFactory connectionFactory()
    {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUsername( environment.getProperty( "spring.rabbitmq.username" ) );
        connectionFactory.setPassword( environment.getProperty( "spring.rabbitmq.password" ) );
        connectionFactory.setVirtualHost( environment.getProperty( "spring.rabbitmq.virtualHost" ) );
        connectionFactory.setHost( environment.getProperty( "spring.rabbitmq.host" ) );
        connectionFactory.setPort( Integer.parseInt( environment.getProperty( "spring.rabbitmq.port" ) ) );
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin()
    {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory() );
        rabbitAdmin.setIgnoreDeclarationExceptions(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate()
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate( connectionFactory() );
        rabbitTemplate.setMessageConverter( jsonMessageConverter() );
        return rabbitTemplate;
    }

    @Bean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory listenerFactory()
    {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory( connectionFactory() );
        factory.setMessageConverter( jsonMessageConverter() );
        //factory.setConcurrentConsumers(3);
        //factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter()
    {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter( mapper );
    }
}
