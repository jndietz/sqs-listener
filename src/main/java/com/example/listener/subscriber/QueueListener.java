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
    public void receiveMessage(Message<String> message) {
        logger.info("Headers");
        message.getHeaders().forEach((key, value) -> logger.info(String.format("%s: %s", key, value)));
        logger.info(String.format("Received: %s", message.getPayload()));
    }
}
