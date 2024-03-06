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

    @Bean
    public FanoutExchange createfanoutExchange(){
        FanoutExchange fanoutExchange = new FanoutExchange("PrintingTaskExchange");
        return fanoutExchange;
    }

    @Bean
    public Queue createTaskQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 10);

        return QueueBuilder.durable("PrintingTaskQueue").withArguments(args).build();
    }

    @Bean
    public Binding createBinding(){
        Binding binding = new Binding("PrintingTaskQueue", Binding.DestinationType.QUEUE
                , "PrintingTaskExchange", "", null);
        return binding;
    }
}
