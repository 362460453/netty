package com.server.schedule;

import com.server.service.IClientManage;
import com.server.utils.SpringContextHolder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author yanlin
 * @Date 2020/4/13
 * @Version V1.0
 **/
@Component
@EnableScheduling
public class MmcScheduleJob {

    /*
    每20秒
     */
//    @Scheduled(cron = "${per.twenty.second}")
    public void checkClient() {
        IClientManage iClientManage= SpringContextHolder.getBean("clientManageImpl");
        iClientManage.checkClient();
    }
}

