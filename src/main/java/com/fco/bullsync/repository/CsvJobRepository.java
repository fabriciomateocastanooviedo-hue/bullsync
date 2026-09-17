package com.fco.bullsync.repository;

import com.fco.bullsync.model.CsvJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvJobRepository extends JpaRepository<CsvJob, Long> {
}
