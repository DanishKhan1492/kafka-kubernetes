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
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id", nullable = false)
    private Integer contactId;

    @Column(name = "contact_type", nullable = false, length = 50)
    private String contactType;

    @Column(name = "contact", nullable = false, length = 200)
    private String contactValue;

    @OneToMany(mappedBy = "contact")
    @ToString.Exclude
    @JsonIgnore
    private Set<Employee> employees = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact tempContact = (Contact) o;
        return contactId.equals(tempContact.contactId) && contactType.equals(tempContact.contactType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactId, contactType);
    }
}