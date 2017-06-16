package org.sunyata.spring.thrift.client.annotation;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.sunyata.spring.thrift.client.PoolConfiguration;
import org.sunyata.spring.thrift.client.ThriftClientAutoConfiguration;
import org.sunyata.spring.thrift.client.ThriftClientBeanPostProcessor;
import org.sunyata.spring.thrift.client.ThriftClientsMapBeanPostProcessor;

import java.lang.annotation.*;

/**
 * Created by leo on 17/6/14.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
@Import({ThriftClientAutoConfiguration.class, PoolConfiguration.class, ThriftClientBeanPostProcessor.class,
        ThriftClientsMapBeanPostProcessor.class})
public @interface EnableThriftClient {

}

