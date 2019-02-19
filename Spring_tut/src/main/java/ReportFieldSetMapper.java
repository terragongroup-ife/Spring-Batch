import model.Report;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FieldSetFactory;
import org.springframework.validation.BindException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

//This class is used to convert the Date to a custom FieldMapper and is used to map the CSV fields to the Report class.
// Add the following code to it:
public class ReportFieldSetMapper implements FieldSetMapper<Report> {

    static Report reportObject;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public Report mapFieldSet(FieldSet fieldSet) throws BindException {

        reportObject = new Report();
        reportObject.setId(fieldSet.readInt(0));
        reportObject.setSales(fieldSet.readBigDecimal(1));
        reportObject.setQuantity(fieldSet.readInt(2));
        reportObject.setStaffName(fieldSet.readString(3));

        String csvDate = fieldSet.readString(4);
        try {
            reportObject.setDate(dateFormat.parse(csvDate));
        }catch (ParseException e){
            e.printStackTrace();
        }

        return reportObject;
    }
}
