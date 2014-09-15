package com.ocyd.jeecgframework.core.timer;

import com.ocyd.jeecgframework.core.util.LogUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Job {

 
//    @Scheduled(cron="*/10 * * * * *") 
//    public void s10(){
//        LogUtil.info("==== 十秒执行一次=======10s");
//    }
//    
//    @Scheduled(cron="0 */1 * * * *") 
//    public void m1(){
//        LogUtil.info("1m");
//    }
    
    /**
     * 每天1点执行一次
     * */
    @Scheduled(cron="0 0 1 * * ?") 
    public void oneOClockPerDay(){
        LogUtil.info("1h");
    }
    
    
    
}