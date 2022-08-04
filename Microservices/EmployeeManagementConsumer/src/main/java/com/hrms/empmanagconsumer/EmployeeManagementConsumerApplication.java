package com.hrms.empmanagconsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hrms.empmanagconsumer.dao.FailedRecordDao;
import com.hrms.empmanagconsumer.entities.FailedRecord;
import com.hrms.empmanagconsumer.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.ByteArrayJsonMessageConverter;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.List;

@SpringBootApplication
@Slf4j
public class EmployeeManagementConsumerApplication {

    KafkaProperties kafkaProperties;
    private FailedRecordDao failedRecordDao;

    public EmployeeManagementConsumerApplication( KafkaProperties kafkaProperties, FailedRecordDao failedRecordDao){
        this.kafkaProperties = kafkaProperties;
        this.failedRecordDao = failedRecordDao;
    }

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementConsumerApplication.class, args);
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        return new ByteArrayJsonMessageConverter(objectMapper());
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory<?,?> kafkaListenerContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
                                                                               ConsumerFactory<Object, Object> kafkaConsumerFactory){

        ConcurrentKafkaListenerContainerFactory<Object, Object> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(concurrentKafkaListenerContainerFactory, kafkaConsumerFactory);
        concurrentKafkaListenerContainerFactory.setConcurrency(4);
        concurrentKafkaListenerContainerFactory.setCommonErrorHandler(getDefaultErrorHandler());

        return concurrentKafkaListenerContainerFactory;
    }

    private DefaultErrorHandler getDefaultErrorHandler() {
        var defaultErrorHandler = new DefaultErrorHandler(consumerRecordRecoverer);
        var exponentialBackOff = new ExponentialBackOff(1_000L, 2);
        exponentialBackOff.setMaxInterval(60000);
        var exceptionList = List.of(JsonProcessingException.class, EntityNotFoundException.class);
        exceptionList.forEach(defaultErrorHandler::addNotRetryableExceptions);

        defaultErrorHandler.setRetryListeners(((consumerRecord, ex, deliveryAttempt) -> log.error("Retry Failed For Records: {}. Delivery Attempt: {}, with Exception: {}", consumerRecord.offset(), deliveryAttempt,ex)));

        return defaultErrorHandler;
    }

    ConsumerRecordRecoverer consumerRecordRecoverer = (consumerRecord, e) ->{
        if(!(e instanceof JsonProcessingException || e instanceof  EntityNotFoundException)){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                ObjectNode objectNode = objectMapper.createObjectNode();
                objectNode.put("offset", consumerRecord.offset());
                objectNode.put("topic", consumerRecord.topic());
                objectNode.put("partition", consumerRecord.partition());
                objectNode.put("key", consumerRecord.key().toString());
                objectNode.put("value", objectMapper.writeValueAsString(consumerRecord.value()));

                FailedRecord failedrecord = new FailedRecord(objectMapper.writeValueAsString(objectNode));
                log.info("Failed Record : {}", failedrecord);
                failedRecordDao.save(failedrecord);
                log.info("Failed Record Saved in DB");
            } catch (JsonProcessingException ex) {
                log.error("Error While Converting Value to JSON", ex);
                throw new RuntimeException(ex);
            }

        }
    };
}
