package how.eventListeners;

import org.springframework.batch.core.ItemReadListener;

public class StepItemReadListener implements ItemReadListener<String> {

    @Override
    public void beforeRead() {
        System.out.println("ItemReadListener - before read");
    }

    @Override
    public void afterRead(String item) {

        System.out.println("ItemReadListener - after read");
    }

    @Override
    public void onReadError(Exception ex) {

        System.out.println("ItemReadListener - onReadError");
    }
}
