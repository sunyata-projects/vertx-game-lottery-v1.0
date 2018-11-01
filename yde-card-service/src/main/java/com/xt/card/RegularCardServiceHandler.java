package com.xt.card;

import com.xt.card.model.RegularCard17;
import com.xt.card.model.RegularCard37;
import com.xt.card.store.CardRegularStore;
import com.xt.yde.GameTypes;
import com.xt.yde.custom.CardConfigInfo;
import com.xt.yde.custom.CurrentCardConfig;
import com.xt.yde.thrift.card.regular.RegularCards;
import com.xt.yde.thrift.card.regular.RegularCardsService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sunyata.octopus.json.Json;

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
public class RegularCardServiceHandler implements RegularCardsService.Iface {

    Logger logger = LoggerFactory.getLogger(EliminateCardServiceHandler.class);
    @Autowired
    CardRegularStore cardRegularStore;

    public void initialize() throws IOException {

    }

    @Autowired
    CurrentCardConfig currentCardConfig;

    @Override
    public RegularCards getCards17() throws TException {
        RegularCard17 card17 = null;
        CardConfigInfo cardConfigInfo = currentCardConfig.getCardConfigInfo(GameTypes.Regular.getValue());
        if (cardConfigInfo != null) {
            card17 = cardRegularStore.getCard17ById(cardConfigInfo.getCardIds().get(0));
        } else {
            card17 = cardRegularStore.getCard17WithRandom();
        }
        RegularCards regularCards = new RegularCards();
        List<String> strings = Arrays.asList(card17.getC_center().split(","));
        List<Integer> collect = strings.stream().map(Integer::valueOf).collect(Collectors.toList());
        regularCards.setCardId(card17.getId()).setCenter(collect);
        logger.info("常规赛随机手牌为:" + Json.encode(regularCards));
        return regularCards;
    }

    @Override
    public RegularCards getCards37(int prizeLevel, String centerId) throws TException {
        //prize_level:1,2,3,4,5,6
        int bombNums = prizeLevel;
        if (prizeLevel == 5 || prizeLevel == 6) {
            bombNums = -1;
        }
        if (prizeLevel == 4) {
            bombNums = 0;
        }
        logger.info("常规赛 bombNums:{},centerId:{}", bombNums, centerId);
        RegularCard37 card37 = null;
        CardConfigInfo cardConfigInfo = currentCardConfig.getCardConfigInfo(GameTypes.Regular.getValue());

        if (cardConfigInfo != null) {
            card37 = cardRegularStore.getCard37ById(cardConfigInfo.getCardIds().get(1));
        } else {
            card37 = cardRegularStore.getCard37(bombNums, centerId);
        }
        RegularCards regularCards = new RegularCards();

        List<String> right = Arrays.asList(card37.getC_right().split(","));
        List<Integer> rightList = right.stream().map(Integer::valueOf).collect(Collectors.toList());

        List<String> left = Arrays.asList(card37.getC_left().split(","));
        List<Integer> leftList = left.stream().map(Integer::valueOf).collect(Collectors.toList());

        List<String> under = Arrays.asList(card37.getC_under().split(","));
        List<Integer> underList = under.stream().map(Integer::valueOf).collect(Collectors.toList());
        regularCards.setCardId(card37.getId()).setRight(rightList).setLeft(leftList).setUnder(underList);

        RegularCard17 card17ById = cardRegularStore.getCard17ById(card37.getCenter_id());
        List<String> center = Arrays.asList(card17ById.getC_center().split(","));
        List<Integer> centerList = center.stream().map(Integer::valueOf).collect(Collectors.toList());
        regularCards.setCenter(centerList);
        logger.info("常规赛prizeLevel:{},centerId:{},牌库:{}", prizeLevel, centerId, Json.encode(regularCards));
        return regularCards;
    }


}