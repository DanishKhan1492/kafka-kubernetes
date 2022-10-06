package com.hrms.empmanagconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AddressDTO(@JsonProperty Integer addressId, @JsonProperty String address1, @JsonProperty String address2,
                         @JsonProperty Integer city, @JsonProperty String zipCode) {
    AddressDTO(String address1, String address2, Integer city){
        this(null, address1, address2, city, null);
    }
}
