package how.itemProcessor;

import hello.PersonItemProcessor;
import how.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class ValidationProcessor implements ItemProcessor<Employee, Employee> {

//    Learn to use ItemProcessor to add business logic after reading the input and before passing it to consoleWriter
//    for writing to the file/database. It should be noted that while it’s possible to return a different datatype
//    than the one provided as input, it’s not necessary.

//    Returning null from ItemProcessor indicates that the item should not be continued to be processed.

    private static final Logger log = LoggerFactory.getLogger(ValidationProcessor.class);

    @Override
    public Employee process(Employee employee) throws Exception {

        if (employee.getId() == null){
            System.out.println("Invalid employee id - " + employee.getId());
            return null;
        }
        try {
            if (Integer.valueOf(employee.getId()) <= 0){
                System.out.println("Invalid employee id - " + employee.getId());
                return null;
            }
        }catch (NumberFormatException e){
            System.out.println("Invalid employee id - " + employee.getId());
            return null;
        }
        final String firstName = employee.getFirstName().toUpperCase();
        final String lastName = employee.getLastName().toUpperCase();
        final String id = employee.getId();

        Employee transformedEmployee = new Employee(id, firstName, lastName);
        log.info("Converting (" +employee + ") into (" + transformedEmployee + ")");
        return transformedEmployee;
    }
}
