package com.xt.card;

import com.xt.card.model.MissionCard;
import com.xt.card.store.CardMissionStore;
import com.xt.yde.GameTypes;
import com.xt.yde.custom.CardConfigInfo;
import com.xt.yde.custom.CurrentCardConfig;
import com.xt.yde.thrift.card.mission.MissionCards;
import com.xt.yde.thrift.card.mission.MissionCardsService;
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
public class MissionCardServiceHandler implements MissionCardsService.Iface {

    Logger logger = LoggerFactory.getLogger(EliminateCardServiceHandler.class);
    @Autowired
    CardMissionStore cardRegularStore;

    public void initialize() throws IOException {

    }

    @Autowired
    CurrentCardConfig currentCardConfig;

    @Override
    public MissionCards getCards(boolean lose, int missionIndex) throws TException {
        logger.info("闯关赛牌库是否必输{}", lose);
        MissionCard cards = null;
        CardConfigInfo cardConfigInfo = currentCardConfig.getCardConfigInfo(GameTypes.Mission.getValue());
        if (cardConfigInfo != null) {
            String s = cardConfigInfo.getCardIds().get(missionIndex);
            cards = cardRegularStore.getCardsById(s);
        } else {
            cards = cardRegularStore.getCards(lose, missionIndex);
        }

        logger.info("闯关赛牌库id:{}", cards.getId());
        MissionCards result = new MissionCards();

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


}