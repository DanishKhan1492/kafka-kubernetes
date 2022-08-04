package com.hrms.empmanagconsumer.service.consumers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hrms.empmanagconsumer.dto.EmployeeDTO;
import com.hrms.empmanagconsumer.exceptions.EntityNotFoundException;
import com.hrms.empmanagconsumer.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmployeeConsumer {

    private final EmployeeService employeeService;

    EmployeeConsumer(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @KafkaListener(groupId = "employee-topic-group", topicPartitions = {@TopicPartition(topic = "employee-topic", partitions = "0")})
    public void consumerEmployeeMessages(EmployeeDTO employee) throws EntityNotFoundException, JsonProcessingException {
        log.info("Employee: {}", employee);
        employeeService.saveEmployee(employee);
    }
}
