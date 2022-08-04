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
@Table(name = "country")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "country_id", nullable = false)
    private Integer id;

    @Column(name = "country_name", nullable = false, length = 100)
    private String countryName;

    @OneToMany(mappedBy = "country")
    @ToString.Exclude
    @JsonIgnore
    private Set<City> cities = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return id.equals(country.id) && countryName.equals(country.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, countryName);
    }
}