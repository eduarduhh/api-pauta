package br.com.eduardo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	public static final String NOME_FILA = "FilaUsuario";
    public static final String NOME_EXCHANGE = "ExchangeUsuario";
    public static final String ROUTING_KEY = "criarUsuario";

    @Bean
    DirectExchange usuarioExchange() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    @Bean
    Queue queue() {
        return QueueBuilder.durable(NOME_FILA).build();
    }

    @Bean
    Binding binding() {
        return BindingBuilder.bind(queue()).to(usuarioExchange()).with(ROUTING_KEY);
    }

}
