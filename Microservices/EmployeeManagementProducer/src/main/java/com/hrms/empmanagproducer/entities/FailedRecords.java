package com.hrms.empmanagproducer.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "failed_records")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FailedRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "failed_record")
    private String failedRecordJson;
}
