package com.example.listener.subscriber;

import com.example.listener.model.AnotherEventModel;
import com.example.listener.model.TestModel;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class QueueListener {
    private static final Logger logger = Logger.getLogger(QueueListener.class.getName());

    // {"key": "value", "hello": "world"}
    @SqsListener("notification-queue")
    public void receiveMessage(Message<TestModel> message) {
        logger.info("Received a test-event!");
        logger.info(message.getPayload().getKey());
        logger.info(message.getPayload().getHello());
    }

    // {"key": "value"}
    @SqsListener("notification-queue")
    public void receiveAnotherTestEvent(AnotherEventModel message) {
        logger.info("Received another-test-event!");
        logger.info(message.getKey());
    }
}
