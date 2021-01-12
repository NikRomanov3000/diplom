package ru.rsuog.core.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ru.rsuog.core.service.ReceiptService;

@EnableRabbit
@Component
public class RabbitMqListener {
  private final ReceiptService receiptService;
  private static final Logger logger = LoggerFactory.getLogger(ReceiptService.class);

  public RabbitMqListener(ReceiptService receiptService) {
    this.receiptService = receiptService;
  }

  @RabbitListener(queues = "paymentQueue")
  public void processPaymentQueue(String message) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    PaymentInfo paymentInfoFromRabbit;

    logger.info("New massage from RabbitMQ: " + message);

    try {
      paymentInfoFromRabbit = objectMapper.readValue(message, PaymentInfo.class );
    } catch (JsonProcessingException ex){
      throw new RuntimeException("Message from RabbitMQ does not match PaymentInfo format");
    }

    receiptService.updateReceipt(paymentInfoFromRabbit);
  }
}
