package com.javainuse.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.javainuse.listener.JobCompletionListener;
import com.javainuse.step.Processor;
import com.javainuse.step.Reader;
import com.javainuse.step.Writer;

@Configuration
public class BatchConfig {
//
//	@Autowired
//	public JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
	//public StepBuilderFactory stepBuilderFactory;
	
//	@Autowired
//	Step orderStep1;

//	@Bean
//	public Job processJob(JobRepository jobRepository) {
//		return new JobBuilder("processJob",jobRepository)
//				.incrementer(new RunIdIncrementer()).listener(listener())
//				.flow(orderStep1).end().build();
//	}
//	
	
	@Bean
    public Job importCustomers(JobRepository jobRepository,
    		JobExecutionListener listener, Step step1) {
		System.out.println("JOB IS CALLED");
      return new JobBuilder("processJob", jobRepository) // here
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(step1)
        .end()
        .build();
    }

//	@Bean
//	public Step orderStep1(JobRepository jobRepository) {
//		return new StepBuilder("orderstep1",jobRepository).<String, String> chunk(1)
//				.reader(new Reader()).processor(new Processor())
//				.writer(new Writer()).build();
//	}

	
	@Bean
    public Step orderStep1(JobRepository jobRepository,
            PlatformTransactionManager transactionManager) {
		System.out.println("STEP IS CALLED");
          return new StepBuilder("step1", jobRepository) // here
            .<String, String> chunk(10,transactionManager)
            .reader(new Reader())
            .processor(new Processor())
            .writer(new Writer())
           // .taskExecutor(taskExecutor())
            .build();
        }
	
	
	
	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

}
