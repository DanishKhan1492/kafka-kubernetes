package com.hrms.empmanagconsumer.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hrms.empmanagconsumer.dao.*;
import com.hrms.empmanagconsumer.dto.EmployeeDTO;
import com.hrms.empmanagconsumer.entities.Employee;
import com.hrms.empmanagconsumer.exceptions.EntityNotFoundException;
import com.hrms.empmanagconsumer.mappers.EmployeeMapper;
import com.hrms.empmanagconsumer.service.EmployeeService;
import com.hrms.empmanagconsumer.service.producers.StatusProducer;
import com.hrms.empmanagconsumer.util.RecordStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;
    private final CityDao cityDao;
    private final EmployeeMapper employeeMapper;
    private final DepartmentDao departmentDao;
    private final EmployeeTypeDoa employeeTypeDoa;
    private final OrganizationDao organizationDao;
    private final StatusProducer statusProducer;

    EmployeeServiceImpl(EmployeeDao employeeDao, EmployeeMapper employeeMapper, CityDao cityDao, DepartmentDao departmentDao,
                        EmployeeTypeDoa employeeTypeDoa, OrganizationDao organizationDao, StatusProducer statusProducer){
        this.employeeDao=employeeDao;
        this.employeeMapper = employeeMapper;
        this.cityDao = cityDao;
        this.departmentDao = departmentDao;
        this.employeeTypeDoa = employeeTypeDoa;
        this.organizationDao = organizationDao;
        this.statusProducer = statusProducer;
    }

    @Override
    @Transactional
    public void saveEmployee(EmployeeDTO employeeDTO) throws EntityNotFoundException, JsonProcessingException {
        Employee employee = employeeMapper.dtoToEntity(employeeDTO);
        if(employeeDTO.address() != null && employeeDTO.address().city() != null){
            cityDao.findById(employeeDTO.address().city()).ifPresent(value -> employee.getAddress().setCity(value));
        }
        var department = departmentDao.findById(employeeDTO.departmentId());
        if(department.isPresent()){
            employee.setDepartment(department.get());
        }else{
            throw new EntityNotFoundException("Department Not Found with ID: "+ employeeDTO.departmentId());
        }

        var employeeType = employeeTypeDoa.findById(employeeDTO.employeeTypeId());
        if(employeeType.isPresent()){
            employee.setEmployeeType(employeeType.get());
        }else{
            throw new EntityNotFoundException("Employee Type Not Found with ID: "+ employeeDTO.employeeTypeId());
        }

        var organization = organizationDao.findById(employeeDTO.orgId());
        if(organization.isPresent()){
            employee.setOrg(organization.get());
        }else{
            throw new EntityNotFoundException("Organization Not Found with ID: "+ employeeDTO.orgId());
        }
        employeeDao.save(employee);

        statusProducer.postStatus(RecordStatus.SUCCESS, "create", employee, employee.getContact().getContactValue());
    }

    @Override
    public void updateEmployee(EmployeeDTO employee) {
        // Update functionality will be added later
    }
}
