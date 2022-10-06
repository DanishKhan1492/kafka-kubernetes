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
@Table(name = "organization")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id", nullable = false)
    private Integer id;

    @Column(name = "org_name", nullable = false)
    private String orgName;

    @Column(name = "org_description", length = 500)
    private String orgDescription;

    @OneToMany(mappedBy = "org")
    @ToString.Exclude
    @JsonIgnore
    private Set<Employee> employees = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return id.equals(that.id) && orgName.equals(that.orgName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orgName);
    }
}