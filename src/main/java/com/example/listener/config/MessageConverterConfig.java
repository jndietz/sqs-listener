package com.example.listener.config;

import com.example.listener.model.AnotherEventModel;
import com.example.listener.model.TestModel;
import io.awspring.cloud.sqs.config.SqsMessageListenerContainerFactory;
import io.awspring.cloud.sqs.support.converter.SqsMessagingMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

@Configuration
public class MessageConverterConfig {

    @Bean
    SqsMessageListenerContainerFactory<Object> defaultSqsListenerContainerFactory(SqsAsyncClient sqsAsyncClient) {
        return SqsMessageListenerContainerFactory
                .builder()
                .sqsAsyncClient(sqsAsyncClient)
                .configure(sqsContainerOptionsBuilder -> {
                    sqsContainerOptionsBuilder.messageConverter(sqsMessagingMessageConverter());
                    sqsContainerOptionsBuilder.build();
                })
                .build();
    }

    private SqsMessagingMessageConverter sqsMessagingMessageConverter() {
        SqsMessagingMessageConverter converter = new SqsMessagingMessageConverter();
        MappingJackson2MessageConverter payloadConverter = new MappingJackson2MessageConverter();

        converter.setPayloadTypeHeader("event-type");

        converter.setPayloadTypeMapper(message -> {
            String eventTypeHeader = message.getHeaders().get("event-type", String.class);

            if (eventTypeHeader == null) {
                throw new MessagingException(message, "An event-type header was not provided.");
            }

            if (eventTypeHeader.equals("test-event")) {
                return TestModel.class;
            } else if (eventTypeHeader.equals("another-event")) {
                return AnotherEventModel.class;
            }
            throw new MessagingException(message, String.format("The provided event-type \"%s\" isn't handled by the message converter.", eventTypeHeader));
        });

        converter.setPayloadMessageConverter(payloadConverter);

        return converter;
    }
}
