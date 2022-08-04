package com.hrms.empmanagconsumer.service.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hrms.empmanagconsumer.util.RecordStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class StatusProducer {

    private static final String STATUS_TOPIC = "record-status-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;

    StatusProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public <T> void postStatus(RecordStatus recordStatus, String action, T data, String email) throws JsonProcessingException {
        ObjectNode statusData = objectMapper.createObjectNode();
        statusData.put("record-status", String.valueOf(recordStatus));
        statusData.put("action", action);
        statusData.put("actual-data", objectMapper.writeValueAsString(data));
        statusData.put("email", email);
        kafkaTemplate.send(STATUS_TOPIC, objectMapper.writeValueAsString(statusData));
    }

}
