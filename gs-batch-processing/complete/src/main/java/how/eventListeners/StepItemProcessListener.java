package how.eventListeners;

import org.springframework.batch.core.ItemProcessListener;

public class StepItemProcessListener implements ItemProcessListener {
    @Override
    public void beforeProcess(Object item) {
        System.out.println("itemProcessListener - before process");
    }

    @Override
    public void afterProcess(Object item, Object result) {

        System.out.println("itemProcessListener - afterProcess");
    }

    @Override
    public void onProcessError(Object item, Exception e) {

        System.out.println("itemProcessListener - onProcessError");
    }
}
