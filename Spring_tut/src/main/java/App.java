import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    static Job job;
    static JobLauncher jobLauncher;
    static ApplicationContext context;

    private static String[] springConfig = {"spring/batch/jobs/spring-beans.xml"};

    public static void main(String[] args){
        //Loading the Bean Definition From The Spring Configuration File
        context = new ClassPathXmlApplicationContext(springConfig);

        job = (Job) context.getBean("helloWorldJob");
        jobLauncher = (JobLauncher) context.getBean("jobLauncher");

        try {
            JobExecution execution = jobLauncher.run(job,new JobParameters());
            System.out.println("Exit Status : " + execution.getExitStatus());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Done");


    }
}
