package com.xt.landlords.message;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leo on 17/5/10.
 */


@Configuration
public class MessageConfig {

    @Value("${yde.rabbit.queue:ydeQueue}")
    public String rabbitQueueReceive;

    @Value("${yde.rabbit.exchange:ydeExchange}")
    public String rabbitExchangeReceive;


    @Value("${yde.rabbit.connection.host}")
    public String host;

    @Value("${yde.rabbit.connection.port}")
    public String port;
    @Value("${yde.rabbit.connection.vhost}")
    public String vHost;
    @Value("${yde.rabbit.connection.user}")
    public String user;
    @Value("${yde.rabbit.connection.password}")
    public String password;

    @Bean
    Queue queue() {
        return new Queue(rabbitQueueReceive, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(rabbitExchangeReceive);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(rabbitQueueReceive);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory result = new CachingConnectionFactory();
        result.setHost(host);
        result.setPort(Integer.parseInt(port));
        result.setVirtualHost(vHost);
        result.setUsername(user);
        result.setPassword(password);
        result.setConnectionTimeout(6000);
        return result;
    }

}