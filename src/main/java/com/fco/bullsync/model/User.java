package com.fco.bullsync.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users")
@Data
public class User {
    @OneToMany(mappedBy = "username")
    private List<CsvJob> jobs;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    
}
