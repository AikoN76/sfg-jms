package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

/**
 * HelloSender
 * Created by Vincenzo
 * on the 01/02/2020 18:50
 **/
@RequiredArgsConstructor
@Component
@Slf4j
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
       HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, message);

        log.info("The message has been sent!");
    }

    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage() throws JMSException {
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")
                .build();

        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RECEIVE_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloWorldMessage = null;
                try {
                    helloWorldMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloWorldMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");
                    log.info("Sending Hello!");
                    return helloWorldMessage;
                } catch (JsonProcessingException ex) {
                    throw new JMSException("BOOM!");
                }
            }
        });

        log.info("The message has been received: {}", receivedMessage.getBody(String.class));
    }
}
