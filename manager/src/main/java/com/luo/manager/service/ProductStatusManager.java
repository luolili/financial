package com.luo.manager.service;

import com.luo.api.event.ProductStatusEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductStatusManager {
    private static Logger logger = LoggerFactory.getLogger(ProductStatusManager.class);
    @Autowired
    private JmsTemplate jmsTemplate;
    private static final String MQ_DEST = "VirtualTopic.PRODUCT_STATUS";

    public void changeStatus(String id, String status) {
        ProductStatusEvent event = new ProductStatusEvent();
        event.setId(id);
        event.setStatus(status);
        logger.info("send msg:{}", event);
        jmsTemplate.convertAndSend(MQ_DEST, event);

    }
}
