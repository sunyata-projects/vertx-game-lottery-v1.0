package com.xt.landlords.ai;

import com.xt.yde.thrift.ai.AIService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leo on 17/6/12.
 */
@Configuration
public class AIServerConfig {
    @Bean
    public TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }

    @Bean
    public ServletRegistrationBean thriftBookServlet(TProtocolFactory protocolFactory, AIServiceHandler handler) {
        TServlet tServlet = new TServlet(new AIService.Processor<>(handler), protocolFactory);

        return new ServletRegistrationBean(tServlet, "/ai");
    }
}
