package com.hrms.empmanagconsumer.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "failedrecords")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class FailedRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "failed_record_id", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "record", nullable = false)
    @NonNull
    private String record;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FailedRecord that = (FailedRecord) o;
        return id.equals(that.id) && record.equals(that.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, record);
    }
}