package com.hrms.empmanagconsumer.mappers;

import com.hrms.empmanagconsumer.dto.EmployeeDTO;
import com.hrms.empmanagconsumer.entities.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "status", expression = "java(asInteger(employee.status()))")
    @Mapping(ignore = true, target = "address.city")
    @Mapping(target = "contact.contactValue", source = "contact.contact")
    Employee dtoToEntity(EmployeeDTO employee);

    default Integer asInteger(boolean status) {
        return status ? 1 : 0;
    }
}
