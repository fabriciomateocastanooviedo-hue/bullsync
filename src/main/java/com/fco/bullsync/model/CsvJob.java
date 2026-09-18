package com.fco.bullsync.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class CsvJob {


    @ManyToOne
    @JoinColumn(name = "username_id")
    private User username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String fileName;
    String status;
    int totalRows;
    int processedRows;
    int  errorRows;
    LocalDateTime createdAt;

}
