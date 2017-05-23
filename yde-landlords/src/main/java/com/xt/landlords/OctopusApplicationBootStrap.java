package com.xt.landlords;

import com.xt.landlords.event.LoginEventListener;
import com.xt.landlords.event.LoginEventMessage;
import com.xt.landlords.event.UserLeftEventListener;
import com.xt.landlords.event.UserLeftEventMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.sunyata.octopus.EventBus;
import org.sunyata.octopus.NettyServer;
import org.sunyata.octopus.OctopusConfiguration;
import org.sunyata.octopus.Server;
import org.sunyata.octopus.store.StoreFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created by leo on 17/4/19.
 */
@Component
public class OctopusApplicationBootStrap implements ApplicationContextAware {
    @Autowired
    OctopusConfiguration configuration;
    Server server;
    private ApplicationContext applicationContext;

    @Autowired
    StoreFactory storeFactory;

    @PostConstruct
    public void start() throws InterruptedException {
        EventBus.setDistributeStoreFactory(storeFactory);
        subscribe();
        server = new NettyServer(configuration);
        server.start();
    }

    private void subscribe() {
        //用户登录
        LoginEventListener loginEventListener = applicationContext.getBean(LoginEventListener.class);
        EventBus.getLocalPubsubStore().subscribe(LoginEventMessage.EventType, loginEventListener, LoginEventMessage
                .class);

        //用户离开游戏
        UserLeftEventListener userLeftEventListener = applicationContext.getBean(UserLeftEventListener.class);
        EventBus.getLocalPubsubStore().subscribe(UserLeftEventMessage.EventType, userLeftEventListener,
                UserLeftEventMessage.class);
    }

    @PreDestroy
    public void stop() {
        server.stop();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
