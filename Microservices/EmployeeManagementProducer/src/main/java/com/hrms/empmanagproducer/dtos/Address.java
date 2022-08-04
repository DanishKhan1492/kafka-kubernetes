package com.hrms.empmanagproducer.dtos;

public record Address(Integer addressId, String address1, String address2, Integer city, String zipCode) {
    Address(String address1, String address2, Integer city){
        this(null, address1, address2, city, null);
    }
}
