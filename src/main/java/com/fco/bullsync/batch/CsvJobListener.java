package com.fco.bullsync.batch;

import com.fco.bullsync.model.JobStatus;
import com.fco.bullsync.repository.CsvJobRepository;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class CsvJobListener implements JobExecutionListener {

    private final CsvJobRepository csvJobRepository;

    public CsvJobListener(CsvJobRepository csvJobRepository) {
        this.csvJobRepository = csvJobRepository;
    }


    @Override
    public void afterJob(JobExecution jobExecution) {
        Long csvJobId = jobExecution.getJobParameters().getLong("jobId");
        csvJobRepository.findById(csvJobId).ifPresent(csvJob -> {
            StepExecution step = jobExecution.getStepExecutions().iterator().next();
            csvJob.setTotalRows((int) step.getReadCount());
            csvJob.setProcessedRows((int) step.getWriteCount());
            csvJob.setErrorRows((int) step.getFilterCount());
            csvJob.setStatus(jobExecution.getStatus() == BatchStatus.COMPLETED
                    ? JobStatus.COMPLETED : JobStatus.FAILED);
            csvJobRepository.save(csvJob);
        });
    }
}
