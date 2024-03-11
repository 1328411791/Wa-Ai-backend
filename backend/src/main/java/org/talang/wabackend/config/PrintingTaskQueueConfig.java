package org.talang.wabackend.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class PrintingTaskQueueConfig {

    @Autowired
    private AmqpAdmin amqpAdmin;

    public static final String PRINTING_EXCHANGE_NAME = "PrintingTaskExchange";

    public static final String PRINTING_QUEUE_NAME = "PrintingTaskQueue";

    @Bean
    public FanoutExchange createfanoutExchange(){
        FanoutExchange fanoutExchange = new FanoutExchange(PRINTING_EXCHANGE_NAME);
        return fanoutExchange;
    }

    @Bean
    public Queue createTaskQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 10);

        return QueueBuilder.durable(PRINTING_QUEUE_NAME).withArguments(args).build();
    }

    @Bean
    public Binding createBinding(){
        Binding binding = new Binding(PRINTING_QUEUE_NAME, Binding.DestinationType.QUEUE
                , PRINTING_EXCHANGE_NAME, "", null);
        return binding;
    }
}
