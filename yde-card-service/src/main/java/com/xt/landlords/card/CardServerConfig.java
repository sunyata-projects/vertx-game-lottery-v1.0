package com.xt.landlords.card;

import com.xt.yde.thrift.card.classic.ClassicCardsService;
import com.xt.yde.thrift.card.crazy.CrazyCardsService;
import com.xt.yde.thrift.card.eliminate.EliminateCardsService;
import com.xt.yde.thrift.card.mission.MissionCardsService;
import com.xt.yde.thrift.card.puzzle.PuzzleCardsService;
import com.xt.yde.thrift.card.rank.RankCardsService;
import com.xt.yde.thrift.card.regular.RegularCardsService;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by leo on 17/6/12.
 */
@Configuration
@ComponentScan("com.xt.yde.custom")
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


    @Bean
    public ServletRegistrationBean thriftMissionServlet(TProtocolFactory protocolFactory, MissionCardServiceHandler
            handler) {
        TServlet tServlet = new TServlet(new MissionCardsService.Processor<>(handler), protocolFactory);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(tServlet, "/mission");
        servletRegistrationBean.setName("mission");
        return servletRegistrationBean;
    }


    @Bean
    public ServletRegistrationBean thriftCrazyServlet(TProtocolFactory protocolFactory, CrazyCardServiceHandler
            handler) {
        TServlet tServlet = new TServlet(new CrazyCardsService.Processor<>(handler), protocolFactory);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(tServlet, "/crazy");
        servletRegistrationBean.setName("crazy");
        return servletRegistrationBean;
    }


    @Bean
    public ServletRegistrationBean thriftRankServlet(TProtocolFactory protocolFactory, RankCardServiceHandler
            handler) {
        TServlet tServlet = new TServlet(new RankCardsService.Processor<>(handler), protocolFactory);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(tServlet, "/rank");
        servletRegistrationBean.setName("rank");
        return servletRegistrationBean;
    }

    @Bean
    public ServletRegistrationBean thriftClassicServlet(TProtocolFactory protocolFactory, ClassicCardServiceHandler
            handler) {
        TServlet tServlet = new TServlet(new ClassicCardsService.Processor<>(handler), protocolFactory);

        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(tServlet, "/classic");
        servletRegistrationBean.setName("classic");
        return servletRegistrationBean;
    }
}
