package com.fco.bullsync.controller;
import com.fco.bullsync.model.CsvJob;
import com.fco.bullsync.repository.CsvJobRepository;
import com.fco.bullsync.service.CsvJobService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@RestController
@RequestMapping("/api/jobs")
public class CsvUploadController {


    private final CsvJobService csvJobService;

    public CsvUploadController( CsvJobService csvJobService) {

        this.csvJobService = csvJobService;
    }


    @PostMapping("/upload")
    public CsvJob uploadCsv(@RequestParam("file") MultipartFile file) {

        return csvJobService.createJob(file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CsvJob> getCsvById(@PathVariable Long id) {
        Optional<CsvJob> job = csvJobService.getJobById(id);

        return job.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
