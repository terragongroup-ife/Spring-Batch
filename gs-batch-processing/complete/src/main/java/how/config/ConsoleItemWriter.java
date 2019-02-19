package how.config;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class ConsoleItemWriter<T> implements ItemWriter <T>{


    @Override
    public void write(List<? extends T> items) throws Exception {
        for (T item: items ){
            System.out.println(item);
        }
    }
}
