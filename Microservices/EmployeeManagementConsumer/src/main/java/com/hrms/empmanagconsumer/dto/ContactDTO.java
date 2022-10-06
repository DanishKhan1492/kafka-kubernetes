package com.hrms.empmanagconsumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContactDTO(@JsonProperty Integer contactId, @JsonProperty String contactType, @JsonProperty String contact) {
}
