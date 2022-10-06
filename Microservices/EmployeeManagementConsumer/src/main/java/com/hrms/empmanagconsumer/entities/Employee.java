package com.hrms.empmanagconsumer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employee")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    private Integer employeeId;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "status")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    @ToString.Exclude
    @JsonIgnore
    private Employee manager;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "org_id", nullable = false)
    @ToString.Exclude
    private Organization org;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "department_id", nullable = false)
    @ToString.Exclude
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "employee_type_id", nullable = false)
    @ToString.Exclude
    private EmployeeType employeeType;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @ToString.Exclude
    private Address address;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_id", nullable = false)
    @ToString.Exclude
    private Contact contact;

    @OneToMany(mappedBy = "manager")
    @ToString.Exclude
    @JsonIgnore
    private Set<Employee> employees = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId.equals(employee.employeeId) && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && dob.equals(employee.dob) && status.equals(employee.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, dob, status);
    }
}