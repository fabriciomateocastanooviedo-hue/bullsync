package com.fco.bullsync.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class CsvJob {


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String fileName;
    // enum status;
    int totalRows;
    int processedRows;
    int  errorRows;
    LocalDateTime createdAt;

}
