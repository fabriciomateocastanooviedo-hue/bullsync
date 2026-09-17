package com.fco.bullsync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
public class User {
    @OneToMany(mappedBy = "user")
    private List<CsvJob> jobs;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user;
    
}
