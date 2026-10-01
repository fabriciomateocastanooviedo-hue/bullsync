package com.fco.bullsync.service;

import com.fco.bullsync.model.CsvJob;
import com.fco.bullsync.repository.CsvJobRepository;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class CsvJobService {

    private final CsvJobRepository csvJobRepository;
    private final JobOperator jobOperator;
    private final Job csvImportJob;

    public CsvJobService(CsvJobRepository csvJobRepository1, JobOperator jobOperator, Job csvInportJob) {
        this.csvJobRepository = csvJobRepository1;
        this.jobOperator = jobOperator;
        this.csvImportJob = csvInportJob;

    }

    public CsvJob createJob(MultipartFile file) throws Exception {

        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        Path filePath = uploadDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        file.transferTo(filePath.toAbsolutePath().toFile());

        CsvJob job = new CsvJob();
        job.setFileName(file.getOriginalFilename());
        job.setStatus("PENDING");
        job.setCreatedAt(LocalDateTime.now());
        job.setTotalRows(0);
        job.setProcessedRows(0);
        job.setErrorRows(0);
        csvJobRepository.save(job);

        var jobParameters = new JobParametersBuilder()
                .addString("filePath", filePath.toAbsolutePath().toString())
                .addLong("jobId", job.getId())
                .toJobParameters();

        jobOperator.start(csvImportJob, jobParameters);

        return job;
    }

    public Optional<CsvJob> getJobById(Long id) {
        return csvJobRepository.findById(id);
    }
}
