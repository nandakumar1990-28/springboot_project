package com.myproject.myproject.helper;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.myproject.myproject.model.Department;

@Configuration
public class DepartmentBatchConfig {

	@Autowired
	private DataSource dataSource;
	
	@Bean
	public FlatFileItemReader<Department> departmentReader()
	{
		return new FlatFileItemReaderBuilder<Department>()
				.name("departmentItemReader")
				.resource(new ClassPathResource("department_data.csv"))
				.delimited()
				.names(new String[] {"departmentName","departmentAddress","departmentCode"})
				.targetType(Department.class)
				.build();
	}
	
	@Bean
	public JdbcBatchItemWriter<Department> departmentWriter()
	{
		return new JdbcBatchItemWriterBuilder<Department>().itemSqlParameterSourceProvider(
				new BeanPropertyItemSqlParameterSourceProvider<Department>())
				.sql("INSERT INTO department (department_name, department_address,department_code) VALUES (:departmentName, :departmentAddress,:departmentCode)")
				.dataSource(dataSource).build();
	}
	
	@Bean
	public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			FlatFileItemReader<Department> reader, JdbcBatchItemWriter<Department> writer)
	{
		return new StepBuilder("step1",jobRepository).<Department,Department>chunk(10,transactionManager)
				.reader(reader)
				.writer(writer)
				.build();
	}

	
	@Bean
	public Job job1(JobRepository jobRepository, Step step1)
	{
		return new JobBuilder("importDepartmenttJob",jobRepository)
				.start(step1)
				.build();
	}
}
