package how.eventListeners;

import org.springframework.batch.core.SkipListener;

public class StepSkipListener implements SkipListener {
    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("StepSkipListener - onSkipRead");
    }

    @Override
    public void onSkipInWrite(Object item, Throwable t) {

        System.out.println("StepSkipListener - onSkipWrite");
    }

    @Override
    public void onSkipInProcess(Object item, Throwable t) {

        System.out.println("StepSkipListener - onSkipInProcess");
    }
}
