package processor;

import model.Report;
import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<Report,Report> {

    public Report process(Report report) throws Exception {
        System.out.println("Processing Item?= " + report);
        return report;
    }
}
