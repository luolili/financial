package com.luo.seller.task;

import com.luo.seller.enums.ChanEnum;
import com.luo.seller.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class VerifyTask {

    @Autowired
    private VerifyService verifyService;

    @Scheduled(cron = "0 0 1,3,5 * * ?")
    public void makeVerificationFile() {
        for (ChanEnum value : ChanEnum.values()) {
            Date yest = new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24);
            verifyService.makeVerificationFile("", value.getChanId(), yest);

        }
    }
}
