package com.xt.landlords.card;

import com.xt.yde.thrift.card.eliminate.EliminateCardsService;
import com.xt.yde.thrift.card.puzzle.PuzzleCardsService;
import com.xt.yde.thrift.regular.RegularCardsService;
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
public class CardServerConfig {
    @Bean
    public TProtocolFactory tProtocolFactory() {
        return new TBinaryProtocol.Factory();
    }

    @Bean
    public ServletRegistrationBean thriftEliminateServlet(TProtocolFactory protocolFactory, EliminateCardServiceHandler
            handler) {
        TServlet tServlet = new TServlet(new EliminateCardsService.Processor<>(handler), protocolFactory);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(tServlet, "/eliminate");
        servletRegistrationBean.setName("eliminate");
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean thriftPuzzleServlet(TProtocolFactory protocolFactory, PuzzleCardServiceHandler
            handler) {
        TServlet tServlet = new TServlet(new PuzzleCardsService.Processor<>(handler), protocolFactory);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(tServlet, "/puzzle");
        servletRegistrationBean.setName("puzzle");
        return servletRegistrationBean;
    }


    @Bean
    public ServletRegistrationBean thriftRegularServlet(TProtocolFactory protocolFactory, RegularCardServiceHandler
            handler) {
        TServlet tServlet = new TServlet(new RegularCardsService.Processor<>(handler), protocolFactory);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(tServlet, "/regular");
        servletRegistrationBean.setName("regular");
        return servletRegistrationBean;
    }
}
