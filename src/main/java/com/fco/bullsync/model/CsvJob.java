package com.fco.bullsync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class CsvJob {


    @ManyToOne
    @JoinColumn(name = "username_id")
    @JsonIgnoreProperties("password")
    private User username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String fileName;
    @Enumerated(EnumType.STRING)
    JobStatus status;
    int totalRows;
    int processedRows;
    int  errorRows;
    LocalDateTime createdAt;

}
