package com.hrms.empmanagproducer.dtos;

import java.time.LocalDate;

public record Employee(Integer employeeId, String firstName, String lastName, LocalDate dob, boolean status
, Double salary, Integer managerId, Integer orgId, Integer employeeTypeId, Integer departmentId, Address address, Contact contact) {

    Employee(String firstName, String lastName, LocalDate dob, boolean status, Integer managerId, Integer orgId, Integer employeeTypeId, Integer departmentId, Contact contact){
        this(null, firstName, lastName, dob, status, null, managerId, orgId, employeeTypeId, departmentId, null, contact);
    }

    Employee(String firstName, String lastName, LocalDate dob, boolean status, Integer managerId, Integer orgId, Integer employeeTypeId, Integer departmentId, Address address, Contact contact){
        this(null, firstName, lastName, dob, status, null, managerId, orgId, employeeTypeId, departmentId, address, contact);
    }
}
