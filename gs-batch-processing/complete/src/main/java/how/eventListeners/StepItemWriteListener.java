package how.eventListeners;

import org.springframework.batch.core.ItemWriteListener;

import java.util.List;

public class StepItemWriteListener implements ItemWriteListener<Number> {

    @Override
    public void beforeWrite(List<? extends Number> items) {
        System.out.println("itemWriteListener - beforeWrite");
    }

    @Override
    public void afterWrite(List<? extends Number> items) {

        System.out.println("itemWriteListener - afterWrite");
    }

    @Override
    public void onWriteError(Exception exception, List<? extends Number> items) {

        System.out.println("itemWriteListener - onWriteError");
    }
}
