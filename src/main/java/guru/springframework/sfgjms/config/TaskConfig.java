package guru.springframework.sfgjms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * TaskConfig
 * Created by Vincenzo
 * on the 01/02/2020 18:40
 **/
@Configuration
@EnableAsync
@EnableScheduling
public class TaskConfig {

    TaskExecutor taskExecutor(){
        return new SimpleAsyncTaskExecutor();
    }

}
