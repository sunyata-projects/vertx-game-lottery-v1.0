package com.xt.card;

import com.xt.card.model.ClassicCard;
import com.xt.card.store.CardClassicStore;
import com.xt.yde.GameTypes;
import com.xt.yde.custom.CardConfigInfo;
import com.xt.yde.custom.CurrentCardConfig;
import com.xt.yde.thrift.card.classic.ClassicCards;
import com.xt.yde.thrift.card.classic.ClassicCardsService;
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
public class ClassicCardServiceHandler implements ClassicCardsService.Iface {

    Logger logger = LoggerFactory.getLogger(EliminateCardServiceHandler.class);
    @Autowired
    CardClassicStore cardClassicStore;

//    @Autowired
//    CardRankStore cardRankStore;

    public void initialize() throws IOException {

    }

    @Autowired
    CurrentCardConfig currentCardConfig;

    @Override
    public ClassicCards getCards(int prizeLevel) throws TException {
        logger.info("经典赛牌库奖等{}", prizeLevel);
        ClassicCard cards = null;
        CardConfigInfo cardConfigInfo = currentCardConfig.getCardConfigInfo(GameTypes.Classic.getValue());
        if (cardConfigInfo != null) {
            String s = cardConfigInfo.getCardIds().get(0);
            if (prizeLevel == 5) {
                cards = cardClassicStore.getCardsById(s);
//                cards = new ClassicCard()
//                        .setC_center(cardsRank.getC_center())
//                        .setC_right(cardsRank.getC_right())
//                        .setC_left(cardsRank.getC_left())
//                        .setC_under(cardsRank.getC_under())
//                        .setPrize_level(cardsRank.getPrize_level())
//                        .setId(cardsRank.getId());
            } else if (prizeLevel == 6) {
                cards = new ClassicCard()

                        .setId("-1")
                        .setC_center("53,52,51,38,25,12,50,37,24,49,48,47,46,45,44,43,42")
                        .setC_under("41,40,39")
                        .setC_left("0,1,2,3,4,5,6,7,8,9,10,11,13,14,15,16")
                        .setC_right("18,19,20,21,22,23,26,27,28,29,30,31,32,33,34,35");
            } else {
                cards = cardClassicStore.getCardsById(s);
//                cards = new ClassicCard()
//                        .setC_center(cardsRank.getC_center())
//                        .setC_right(cardsRank.getC_right())
//                        .setC_left(cardsRank.getC_left())
//                        .setC_under(cardsRank.getC_under())
//                        .setPrize_level(cardsRank.getPrize_level())
//                        .setId(cardsRank.getId());
            }
        } else {
            if (prizeLevel == 5) {
                cards = cardClassicStore.getCards(prizeLevel);
//                cards = new ClassicCard()
//                        .setC_center(cardsRank.getC_center())
//                        .setC_right(cardsRank.getC_right())
//                        .setC_left(cardsRank.getC_left())
//                        .setC_under(cardsRank.getC_under())
//                        .setPrize_level(cardsRank.getPrize_level())
//                        .setId(cardsRank.getId());
            } else if (prizeLevel == 6) {
                cards = new ClassicCard()

                        .setId("-1")
                        .setC_center("53,52,51,38,25,12,50,37,24,49,48,47,46,45,44,43,42")
                        .setC_under("41,40,39")
                        .setC_left("0,1,2,3,4,5,6,7,8,9,10,11,13,14,15,16")
                        .setC_right("18,19,20,21,22,23,26,27,28,29,30,31,32,33,34,35");
            } else {
                cards = cardClassicStore.getCards(prizeLevel);
//                cards = new ClassicCard()
//                        .setC_center(cardsRank.getC_center())
//                        .setC_right(cardsRank.getC_right())
//                        .setC_left(cardsRank.getC_left())
//                        .setC_under(cardsRank.getC_under())
//                        .setPrize_level(cardsRank.getPrize_level())
//                        .setId(cardsRank.getId());
            }
        }

        logger.info("经典赛牌库id:{}", cards.getId());
        ClassicCards result = new ClassicCards();

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
//        CardConfigInfo cardConfigInfo = currentCardConfig.getCardConfigInfo(GameTypes.Classic.getValue());
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