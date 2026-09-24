package com.fco.bullsync.batch;

import com.fco.bullsync.model.Customer;
import com.fco.bullsync.repository.CustomerRepository;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.data.RepositoryItemWriter;
import org.springframework.batch.infrastructure.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.batch.infrastructure.item.file.FlatFileItemReader;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.core.io.ClassPathResource;


@Configuration
public class CsvBatchConfig {
    @Bean
    public Job csvImportJob(JobRepository jobRepository, Step csvImportStep) {
        return new JobBuilder("csvImportJob", jobRepository)
                .start(csvImportStep)
                .build();
    }
    @Bean
    public ItemProcessor<Customer, Customer> customerItemProcessor() {
        return customer -> {
            if (customer.getMail() == null || customer.getMail().isBlank()) {
                return null;
            }
            return customer;
        };
    }


    @Bean
    @StepScope
    public FlatFileItemReader<Customer> customerItemReader(
            @Value("#{jobParameters['filePath']}") String filePath) {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("customerItemReader")
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names("nombre", "mail", "telefono")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Customer.class);
                }})
                .build();
    }


    @Bean
    public Step csvImportStep(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              FlatFileItemReader<Customer> customerItemReader,
                              ItemProcessor<Customer, Customer> customerItemProcessor,
                              RepositoryItemWriter<Customer> customerItemWriter) {
        return new StepBuilder("csvImportStep", jobRepository)
                .<Customer, Customer>chunk(10)
                .transactionManager(transactionManager)
                .reader(customerItemReader)
                .processor(customerItemProcessor)
                .writer(customerItemWriter)
                .build();
    }




        @Bean
    public RepositoryItemWriter<Customer> customerItemWriter(CustomerRepository customerRepository) {
        RepositoryItemWriter<Customer> writer = new RepositoryItemWriter<Customer>(customerRepository);
        writer.setMethodName("save");
        return writer;
    }

}
