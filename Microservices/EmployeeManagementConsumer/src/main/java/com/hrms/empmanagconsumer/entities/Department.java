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
@Table(name = "department")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id", nullable = false)
    private Integer id;

    @Column(name = "dep_name", nullable = false, length = 200)
    private String depName;

    @Column(name = "description", length = 1000)
    private String description;

    @OneToMany(mappedBy = "department")
    @ToString.Exclude
    @JsonIgnore
    private Set<Employee> employees = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return id.equals(that.id) && depName.equals(that.depName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depName);
    }
}