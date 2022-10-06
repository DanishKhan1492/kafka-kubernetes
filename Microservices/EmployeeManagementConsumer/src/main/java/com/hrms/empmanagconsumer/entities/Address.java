package com.hrms.empmanagconsumer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "address")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer addressId;

    @Column(name = "address1", length = 1000)
    private String address1;

    @Column(name = "address2", length = 1000)
    private String address2;

    @Column(name = "zip_code", length = 100)
    private String zipCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "city", nullable = false)
    @ToString.Exclude
    private City city;

    @OneToMany(mappedBy = "address")
    @ToString.Exclude
    @JsonIgnore
    private Set<Employee> employees = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId.equals(address.addressId) && address1.equals(address.address1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, address1);
    }
}