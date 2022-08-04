package com.hrms.empmanagconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import java.time.LocalDate;

public record EmployeeDTO(@JsonProperty Integer employeeId, @JsonProperty String firstName, @JsonProperty String lastName,
                          @JsonDeserialize(using = LocalDateDeserializer.class) @JsonProperty LocalDate dob, @JsonProperty boolean status, @JsonProperty Double salary,
                          @JsonProperty Integer managerId, @JsonProperty Integer orgId, @JsonProperty Integer employeeTypeId,
                          @JsonProperty Integer departmentId, @JsonProperty AddressDTO address, @JsonProperty ContactDTO contact) {

    EmployeeDTO(String firstName, String lastName, LocalDate dob, boolean status, Integer managerId, Integer orgId, Integer employeeTypeId, Integer departmentId, ContactDTO contact){
        this(null, firstName, lastName, dob, status, null, managerId, orgId, employeeTypeId, departmentId, null, contact);
    }

    EmployeeDTO(String firstName, String lastName, LocalDate dob, boolean status, Integer managerId, Integer orgId, Integer employeeTypeId, Integer departmentId, AddressDTO address, ContactDTO contact){
        this(null, firstName, lastName, dob, status, null, managerId, orgId, employeeTypeId, departmentId, address, contact);
    }
}
