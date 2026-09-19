package com.fco.bullsync.service;

import com.fco.bullsync.model.CsvJob;
import com.fco.bullsync.repository.CsvJobRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Component
public class CsvJobService {

    private final CsvJobRepository CsvJobRepository;

    public CsvJobService(CsvJobRepository csvJobRepository) {
        CsvJobRepository = csvJobRepository;
    }

    public CsvJob createJob(@NonNull MultipartFile file) {
        CsvJob job = new CsvJob();
        job.setFileName(file.getOriginalFilename());
        job.setStatus("PENDING");
        job.setCreatedAt(LocalDateTime.now());
        job.setTotalRows(0);
        job.setProcessedRows(0);
        job.setErrorRows(0);

        return CsvJobRepository.save(job);
    }
}
