package how.eventListeners;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecutionListener;

import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.Metric;
import javax.batch.runtime.StepExecution;
import java.io.Serializable;
import java.util.Date;

public class StepResultListener implements StepExecutionListener {

    @Override
    public void beforeStep(org.springframework.batch.core.StepExecution stepExecution) {
        System.out.println("Called before step");
    }

    @Override
    public ExitStatus afterStep(org.springframework.batch.core.StepExecution stepExecution) {
        System.out.println("Called after step");
        return ExitStatus.COMPLETED;
    }
}
