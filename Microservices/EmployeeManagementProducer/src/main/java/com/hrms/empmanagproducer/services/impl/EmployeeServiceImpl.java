package com.hrms.empmanagproducer.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrms.empmanagproducer.dao.FailedRecordDao;
import com.hrms.empmanagproducer.dtos.Employee;
import com.hrms.empmanagproducer.entities.FailedRecords;
import com.hrms.empmanagproducer.services.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private static final String EMPLOYEE_TOPIC = "employee-topic";

    private final KafkaTemplate<String, Employee> kafkaTemplate;

    private final FailedRecordDao failedRecordDao;

    EmployeeServiceImpl(KafkaTemplate<String, Employee> kafkaTemplate, FailedRecordDao failedRecordDao) {
        this.kafkaTemplate = kafkaTemplate;
        this.failedRecordDao= failedRecordDao;
    }

    @Override
    public void saveEmployee(Employee employee) {
        sendData(employee, "Creation");
    }

    @Override
    public void updateEmployee(Employee employee) {
        sendData(employee, "Update");
    }

    private void sendData(Employee employee, String type) {
        final var partition = 0;
        ListenableFuture<SendResult<String, Employee>> futureCallback = kafkaTemplate.send(EMPLOYEE_TOPIC, partition, "Employee-" + employee.employeeId(), employee);
        futureCallback.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(@NonNull Throwable ex) {
                log.error("Error While Sending data to topic {}", EMPLOYEE_TOPIC, ex);
                try {
                    failedRecordDao.save(new FailedRecords(null, new ObjectMapper().writeValueAsString(employee)));
                } catch (JsonProcessingException e) {
                    log.error("Exception while saving Failed Record: ", e);
                }
            }

            @Override
            public void onSuccess(SendResult<String, Employee> result) {
                log.info("Employee Data sent to topic: {}, Partition: {}, Key: Employee-{} for New Employee Record " + type, EMPLOYEE_TOPIC, partition, employee.employeeId());
            }
        });
    }
}
