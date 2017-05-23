package com.xt.yde.job;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by leo on 17/4/19.
 */
@Component
public class ApplicationBootStrap implements ApplicationContextAware {
    //    @Autowired
//    OctopusConfiguration configuration;
    private ApplicationContext applicationContext;

//
//    @Autowired
//    StoreFactory storeFactory;

    @PostConstruct
    public void start() throws Exception {
//        EventBus.setDistributeStoreFactory(storeFactory);
        subscribe();
    }

    private void subscribe() throws Exception {
//        SyncGameModelListener syncGameModelListener = applicationContext.getBean(SyncGameModelListener.class);
//        EventBus.getDistributePubsubStore().subscribe(SyncGameModelMessage.EventType, syncGameModelListener,
//                SyncGameModelMessage.class);
    }

    @PreDestroy
    public void stop() {
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
