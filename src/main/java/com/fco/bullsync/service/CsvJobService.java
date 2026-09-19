package com.fco.bullsync.service;

import com.fco.bullsync.model.CsvJob;
import com.fco.bullsync.repository.CsvJobRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class CsvJobService {

    private final CsvJobRepository csvJobRepository;

    public CsvJobService(CsvJobRepository csvJobRepository1) {
        this.csvJobRepository = csvJobRepository1;

    }

    public CsvJob createJob(@NonNull MultipartFile file) {
        CsvJob job = new CsvJob();
        job.setFileName(file.getOriginalFilename());
        job.setStatus("PENDING");
        job.setCreatedAt(LocalDateTime.now());
        job.setTotalRows(0);
        job.setProcessedRows(0);
        job.setErrorRows(0);

        return csvJobRepository.save(job);
    }


    public Optional<CsvJob> getJobById(Long id) {
        return csvJobRepository.findById(id);
    }



}
