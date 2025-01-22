package com.myproject.myproject.helper;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.myproject.myproject.model.Student;
@Configuration
public class BatchConfig {
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Student> reader()
	{
		return new FlatFileItemReaderBuilder<Student>()
				.name("studentItemReader")
				.resource(new ClassPathResource("student_data.csv"))
				.delimited()
				.names(new String[] {"firstName","lastName"})
				.fieldSetMapper(fieldSet ->
				{
					Student student = new Student();
					student.setFirstName(fieldSet.readString("firstName"));
					student.setLastName(fieldSet.readString("lastName"));
					return student;
				}).build();
	}
	
	
	
	@Bean
	public JdbcBatchItemWriter<Student> writer()
	{
		return new JdbcBatchItemWriterBuilder<Student>().itemSqlParameterSourceProvider(
				new BeanPropertyItemSqlParameterSourceProvider<Student>())
				.sql("INSERT INTO student (first_name, last_name) VALUES (:firstName, :lastName)").dataSource(dataSource).build();
	}
	
	@Bean
	public ItemProcessor<Student, Student> processor() 
	{
	    return new StudentItemProcessor();
	}
	
	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			FlatFileItemReader<Student> reader, JdbcBatchItemWriter<Student> writer,ItemProcessor<Student, Student> studentItemProcessor)
	{
		return new StepBuilder("step",jobRepository).<Student,Student>chunk(10,transactionManager)
				.reader(reader).processor(studentItemProcessor)
				.writer(writer)
				.build();
	}

	
	@Bean
	public Job job(JobRepository jobRepository, Step step)
	{
		return new JobBuilder("importStudentJob",jobRepository)
				.start(step)
				.build();
	}
	
	@Bean
    public CommandLineRunner runJob(JobLauncher jobLauncher, Job job) {
        return args -> {
            try {
                // Create unique JobParameters
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("time", System.currentTimeMillis())  // unique parameter
                        .toJobParameters();

                jobLauncher.run(job, jobParameters);
                System.out.println("Batch job has been invoked.");
            } catch (JobExecutionException e) {
                System.err.println("Job failed: " + e.getMessage());
            }
        };
    }
	
	@Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager(); // For simple batch processing without needing a real database transaction manager.
    }
	
	
	
	

}
