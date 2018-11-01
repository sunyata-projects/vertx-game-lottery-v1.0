package com.xt.card;

import com.xt.card.model.RankCard;
import com.xt.card.store.CardRankStore;
import com.xt.yde.GameTypes;
import com.xt.yde.custom.CardConfigInfo;
import com.xt.yde.custom.CurrentCardConfig;
import com.xt.yde.thrift.card.rank.RankCards;
import com.xt.yde.thrift.card.rank.RankCardsService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Service
//@ThriftController("/regular")
public class RankCardServiceHandler implements RankCardsService.Iface {

    Logger logger = LoggerFactory.getLogger(EliminateCardServiceHandler.class);
    @Autowired
    CardRankStore cardRegularStore;

    public void initialize() throws IOException {

    }

    @Autowired
    CurrentCardConfig currentCardConfig;

    @Override
    public RankCards getCards(int prizeLevel) throws TException {

        logger.info("排位赛牌库奖等{}", prizeLevel);
        RankCard cards = null;

        CardConfigInfo cardConfigInfo = currentCardConfig.getCardConfigInfo(GameTypes.Rank.getValue());
        if (cardConfigInfo != null) {
            String s = cardConfigInfo.getCardIds().get(0);
            cards = cardRegularStore.getCardsById(s);
        } else {
            logger.info("直接从牌库获取");
            cards = cardRegularStore.getCards(prizeLevel);
            //cards = cardRegularStore.getCardsById("441");
        }
//        cards = cardRegularStore.getCardsById("334");//// TODO: 17/11/6
        logger.info("排位赛牌库奖等:{}", cards.getId());
        RankCards result = new RankCards();

        List<String> centerStringList = Arrays.asList(cards.getC_center().split(","));
        List<Integer> center = centerStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
        result.setCardId(cards.getId()).setCenter(center);

        List<String> leftStringList = Arrays.asList(cards.getC_left().split(","));
        List<Integer> left = leftStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
        result.setLeft(left);

        List<String> rightStringList = Arrays.asList(cards.getC_right().split(","));
        List<Integer> right = rightStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
        result.setRight(right);

        List<String> underStringList = Arrays.asList(cards.getC_under().split(","));
        List<Integer> under = underStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
        result.setUnder(under);
        return result;
    }

//    @Override
//    public MissionCards getCards(boolean lose, int missionIndex) throws TException {
//        logger.info("闯关赛牌库是否必输{}", lose);
//        MissionCard cards = null;
//        CardConfigInfo cardConfigInfo = currentCardConfig.getCardConfigInfo(GameTypes.Rank.getValue());
//        if (cardConfigInfo != null) {
//            String s = cardConfigInfo.getCardIds().get(missionIndex);
//            cards = cardClassicStore.getCardsById(s);
//        } else {
//            cards = cardClassicStore.getCards(lose, missionIndex);
//        }
//
//        logger.info("闯关赛牌库id:{}", cards.getId());
//        MissionCards result = new MissionCards();
//
//        List<String> centerStringList = Arrays.asList(cards.getC_center().split(","));
//        List<Integer> center = centerStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
//        result.setCardId(cards.getId()).setCenter(center);
//
//        List<String> leftStringList = Arrays.asList(cards.getC_left().split(","));
//        List<Integer> left = leftStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
//        result.setLeft(left);
//
//        List<String> rightStringList = Arrays.asList(cards.getC_right().split(","));
//        List<Integer> right = rightStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
//        result.setRight(right);
//
//        List<String> underStringList = Arrays.asList(cards.getC_under().split(","));
//        List<Integer> under = underStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
//        result.setUnder(under);
//        return result;
//    }


}