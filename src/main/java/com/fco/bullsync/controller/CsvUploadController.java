package com.fco.bullsync.controller;
import com.fco.bullsync.model.CsvJob;
import com.fco.bullsync.service.CsvJobService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/jobs")
public class CsvUploadController {


    private final CsvJobService CsvJobService;

    public CsvUploadController( CsvJobService csvJobService) {

        this.CsvJobService = csvJobService;
    }

    @PostMapping("/upload")
    public CsvJob uploadCsv(@RequestParam("file") MultipartFile file) {

        return CsvJobService.createJob(file);
    }
}
