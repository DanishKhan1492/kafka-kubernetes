package com.hrms.empmanagconsumer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "employee_type")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class EmployeeType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_type_id", nullable = false)
    private Integer id;

    @Column(name = "emp_type_name", nullable = false, length = 100)
    private String empTypeName;

    @OneToMany(mappedBy = "employeeType")
    @ToString.Exclude
    @JsonIgnore
    private Set<Employee> employees = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeType that = (EmployeeType) o;
        return id.equals(that.id) && empTypeName.equals(that.empTypeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, empTypeName);
    }
}