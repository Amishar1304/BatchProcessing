package com.beginner.BootBatchExample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.beginner.BootBatchExample.entity.Product;
import com.beginner.BootBatchExample.repo.ProductRepository;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class BatchConfig {

	@Autowired
	private ProductRepository productRepository;

//READER
	@Bean
	public FlatFileItemReader<Product> productReader() {
		FlatFileItemReader<Product> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("src/main/resources/data.csv"));
		itemReader.setName("Item-reader");
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());
		return itemReader;
	}

	// MAPPER

	private LineMapper<Product> lineMapper() {

		DefaultLineMapper<Product> lineMapper = new DefaultLineMapper<Product>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setNames("productId", "title", "description", "price", "discount");
		lineTokenizer.setStrict(false);

		BeanWrapperFieldSetMapper<Product> fieldSetMapper = new BeanWrapperFieldSetMapper<Product>();
		fieldSetMapper.setTargetType(Product.class);

		lineMapper.setFieldSetMapper(fieldSetMapper);
		lineMapper.setLineTokenizer(lineTokenizer);

		return lineMapper;
	}

//PROCESSOR
	@Bean
	public CustomItemProcessor itemProcessor() {
		return new CustomItemProcessor();
	}

//WRITER
	@Bean
	public RepositoryItemWriter<Product> itemWriter() {
		RepositoryItemWriter<Product> writer = new RepositoryItemWriter<Product>();
		writer.setRepository(productRepository);
		writer.setMethodName("save");
		return writer;
	}

	// STEP
	@SuppressWarnings("unchecked")
	@Bean
	public Step step(JobRepository jobRepository, PlatformTransactionManager manager) {
		return new StepBuilder("DATA-step", jobRepository).<Product, Product>chunk(100, manager).reader(productReader())
				.processor(itemProcessor()).writer(itemWriter()).build();
	}

	// JOB
	@Bean
	public Job job(JobRepository repository, PlatformTransactionManager manager) {
		return new JobBuilder("DATA-job", repository).flow(step(repository, manager)).end().build();
	}

}