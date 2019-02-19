package how.config;

import how.eventListeners.*;
import how.itemProcessor.ValidationProcessor;
import how.model.Employee;
import how.tasks.MyTaskOne;
import how.tasks.MyTaskTwo;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Value("/input/inputData*.csv")
    private Resource[] resources;

    @Value("/input/inputData1.csv")
    private Resource resource;

    private Resource outputResourse = new FileSystemResource("output/outputData.csv");

    public FlatFileItemWriter<Employee> writer(){
        //create writer instance
        FlatFileItemWriter<Employee> writer = new FlatFileItemWriter<Employee>();

        //set output file location
        writer.setResource(outputResourse);

        //all job repititions should append to the same output file
        writer.setAppendAllowed(true);

        //Name field values sequence based on object properties
        writer.setLineAggregator(new DelimitedLineAggregator<Employee>() {

            {

                setDelimiter(",");
                setFieldExtractor(new BeanWrapperFieldExtractor<Employee>(){
                    {
                        setNames(new String[] {"id", "firstName", "lastName"});
                    }
                });
            }


        });

        return writer;
    }


    @Primary
    @Bean
    public Job readCSVFilesJob(){
        return jobs
                .get("readCSVFilesJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean(name = "step1")
    public Step step1(){
        return steps
                .get("step1")
                .<Employee, Employee>chunk(1)
                .reader(multiResourceItemReader())
                .processor(processor())
                .writer(writer())

                .build();
    }

    @Bean
    public ItemProcessor<Employee, Employee> processor(){
        return new ValidationProcessor();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FlatFileItemReader<Employee> reader(){
        //Create reader instance
        FlatFileItemReader<Employee> reader = new FlatFileItemReader<Employee>();


        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(multilineMapper());

        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(1);

        //Set input file location
        //reader.setResource(resource);
        return reader;
    }
    @Bean
    public LineMapper<Employee> multilineMapper(){
        DefaultLineMapper<Employee> lineMapper = new DefaultLineMapper<Employee>(){
            {
                setLineTokenizer(new DelimitedLineTokenizer(){
                    {
                        setNames(new String[] {"id","firstName", "lastName"});
                    }
                });

                setFieldSetMapper(new BeanWrapperFieldSetMapper<Employee>(){
                    {
                        setTargetType(Employee.class);
                    }
                });
            }
        };

        return lineMapper;
    }

    @Bean
    public LineMapper<Employee> lineMapper(){
        DefaultLineMapper<Employee>  lineMapper = new DefaultLineMapper<Employee>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

        lineTokenizer.setNames(new String[] {"id","firstName","lastName"});
        lineTokenizer.setIncludedFields(new int[] {0,1,2});
        BeanWrapperFieldSetMapper<Employee> fieldSetMapper = new BeanWrapperFieldSetMapper<Employee>();

        fieldSetMapper.setTargetType(Employee.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    //when reading from multiple resources
    @Bean
    public MultiResourceItemReader<Employee> multiResourceItemReader(){
        MultiResourceItemReader<Employee> resourceItemReader = new MultiResourceItemReader<Employee>();

        resourceItemReader.setResources(resources);
        resourceItemReader.setDelegate(reader());
        return resourceItemReader;


    }
    @Bean
    public ConsoleItemWriter<Employee> consoleWriter(){

        return new ConsoleItemWriter<Employee>();
    }

//    @Bean
//    public Step stepOne(){
//        return steps.get("stepOne")
//                .tasklet(new MyTaskOne())
//                .listener(new StepResultListener())
//                .listener(new StepItemReadListener())
//                .listener(new StepItemProcessListener())
//                .listener(new StepItemWriteListener())
//                .listener(new StepSkipListener())
//                .build();
//    }
//
//    @Bean
//    public Step stepTwo(){
//        return steps.get("stepTwo")
//                .tasklet(new MyTaskTwo())
//                .listener(new StepResultListener())
//                .listener(new StepItemReadListener())
//                .listener(new StepItemProcessListener())
//                .listener(new StepItemWriteListener())
//                .listener(new StepSkipListener())
//                .build();
//    }
//
//    @Bean(name = "demoJobOne")
//    public Job demoJobOne(){
//        return jobs.get("demoJobOne")
//                .incrementer(new RunIdIncrementer())
//                .listener(new JobResultListener())
//                .start(stepOne())
//                .next(stepTwo())
//                .build();
//    }
//
//    @Bean(name = "demoJobTwo")
//    public Job demoJobTwo(){
//        return jobs.get("demoJobTwo")
//                .flow(stepOne())
//                .build()
//                .build();
//    }
}
