package com.luo.quartz;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HaloJob.class)
public class QuartzTest {

    @Test
    public void test() throws SchedulerException {
        StdSchedulerFactory factory = new StdSchedulerFactory();
        Scheduler scheduler = factory.getScheduler();

        JobDetail jobDetail = JobBuilder.newJob(HaloJob.class)
                .withIdentity("jobName", "jobGroup")
                .build();

        Date statTime = new Date(System.currentTimeMillis() + 3000);
        scheduler.start();//启动该任务

        // 5. 创建Trigger
        //使用SimpleScheduleBuilder或者CronScheduleBuilder
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("jobTriggerName", "jobTriggerGroup")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")) //两秒执行一次
                .build();

        // 6. 注册任务和定时器
        scheduler.scheduleJob(jobDetail, trigger);
    }
}
