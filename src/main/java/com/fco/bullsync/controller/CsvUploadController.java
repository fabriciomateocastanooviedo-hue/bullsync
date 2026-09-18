package com.fco.bullsync.controller;
import com.fco.bullsync.model.CsvJob;
import com.fco.bullsync.repository.CsvJobRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/jobs")
public class CsvUploadController {

    private final CsvJobRepository csvJobRepository;

    public CsvUploadController(CsvJobRepository csvJobRepository) {
        this.csvJobRepository = csvJobRepository;
    }

    @PostMapping("/upload")
    public CsvJob uploadCsv(@RequestParam("file") MultipartFile file) {
        CsvJob job = new CsvJob();
        job.setFileName(file.getOriginalFilename());
        job.setStatus("PENDING");
        job.setCreatedAt(LocalDateTime.now());
        job.setTotalRows(0);
        job.setProcessedRows(0);
        job.setErrorRows(0);

        return csvJobRepository.save(job);
    }
}
