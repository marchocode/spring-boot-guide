package xyz.chaobei.schedule.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@Slf4j
public class Scheduler {


    @Scheduled(cron = "0 0 */1 * * ?")
    public void cronJob() {
        log.info("One Hour exec");
    }


    @Scheduled(cron = "0/1 * * * * ?")
    public void minus() {
       log.info("go go go !!!");
    }


}
