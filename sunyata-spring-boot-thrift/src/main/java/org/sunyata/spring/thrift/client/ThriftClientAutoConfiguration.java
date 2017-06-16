package org.sunyata.spring.thrift.client;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.sunyata.spring.thrift.client.sleuth.ThriftHttpTransportSpanInjector;
import org.apache.thrift.transport.TTransport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.sleuth.SpanInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by aleksandr on 27.06.16.
 */
@Configuration
public class ThriftClientAutoConfiguration {
    @Bean
    public TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }
    @Bean
    @ConditionalOnMissingBean(name = "thriftTransportSpanInjector")
    SpanInjector<TTransport> thriftTransportSpanInjector() {
        return new ThriftHttpTransportSpanInjector();
    }
}
