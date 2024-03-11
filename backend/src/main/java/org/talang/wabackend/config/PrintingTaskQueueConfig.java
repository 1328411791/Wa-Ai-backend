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


    public static final String PRINTING_DEAD_EXCHANGE = "PrintingDeadExChange";
    public static final String PRINTING_DEAD_QUEUE_NAME = "PrintingDeadTaskQueue";

    public static final String TASK_DEAD_KEY = "deal.task";

    @Bean
    public FanoutExchange createfanoutExchange(){
        FanoutExchange fanoutExchange = new FanoutExchange(PRINTING_EXCHANGE_NAME);
        return fanoutExchange;
    }

    @Bean
    public Queue createTaskQueue(){
        Map<String, Object> args = new HashMap<>();
        args.put("x-max-priority", 10);
        args.put("x-dead-letter-exchange", PRINTING_DEAD_QUEUE_NAME);
        args.put("x-dead-letter-routing-key", TASK_DEAD_KEY);
        return QueueBuilder.durable(PRINTING_QUEUE_NAME).withArguments(args).build();
    }

    @Bean
    public Binding createBinding(){
        Binding binding = new Binding(PRINTING_QUEUE_NAME, Binding.DestinationType.QUEUE
                , PRINTING_EXCHANGE_NAME, "", null);
        return binding;
    }


    @Bean
    public Queue createTaskDeadQueue(){
        return QueueBuilder.durable(PRINTING_DEAD_QUEUE_NAME).build();
    }

    @Bean
    public Exchange createDeadExChange(){
        TopicExchange topicExchange = new TopicExchange(PRINTING_DEAD_EXCHANGE, true, false);
        return topicExchange;
    }

    @Bean
    public Binding createDeadBinding(){
        Binding binding = new Binding(PRINTING_DEAD_QUEUE_NAME, Binding.DestinationType.QUEUE
                , PRINTING_DEAD_EXCHANGE, TASK_DEAD_KEY, null);
        return binding;
    }
}
