package com.xt.card;

import com.xt.card.model.ClassicCard;
import com.xt.card.store.CardClassicStore;
import com.xt.yde.custom.CurrentCardConfig;
import com.xt.yde.thrift.card.classic.ClassicCardsForRandom;
import com.xt.yde.thrift.card.classic.ClassicCardsServiceForRandom;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Service
//@ThriftController("/regular")
public class ClassicCardServiceHandler implements ClassicCardsServiceForRandom.Iface {

    Logger logger = LoggerFactory.getLogger(ClassicCardServiceHandler.class);
    @Autowired
    CardClassicStore cardClassicStore;

    //    @Autowired
//    CardRankStore cardRankStore;
    public ClassicCardServiceHandler() {
        initialize();
    }

    public void initialize() {
        /*
        * vdbxe65a2z
        * dxereeyo5w
        * m8ofjtolr4
        * agd6eehedz
        * xqwoen5i66
        * wn3jeztew5
        * q4iibpf710
        * 3ixzxrgcu6
        * 3jquen44og
        * ejyh43e7lp
        * */
        map.put("vdbxe65a2z", "639");
        map.put("dxereeyo5w", "640");
        map.put("m8ofjtolr4", "739");
        map.put("agd6eehedz", "740");
        map.put("xqwoen5i66", "839");
        map.put("wn3jeztew5", "840");
        map.put("q4iibpf710", "939");
        map.put("3ixzxrgcu6", "940");
        map.put("3jquen44og", "1039");
        map.put("ejyh43e7lp", "1040");

    }

    Random random = new Random();

    public boolean is50Percent() {
        int i = nextInt(1, 2);
        return i == 1;
    }

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    @Autowired
    CurrentCardConfig currentCardConfig;
    HashMap<String, String> map = new HashMap<>();

    @Override
    public ClassicCardsForRandom getCards(int prizeLevel) throws TException {
        logger.info("经典赛牌库奖等{}", prizeLevel);
        ClassicCard cards = null;
        int i = nextInt(0, 9);
        List keys = new ArrayList(map.keySet());
        String o = (String) keys.get(i);
        String s = map.get(o);
        cards = cardClassicStore.getCardsById(s);
        ClassicCardsForRandom result = new ClassicCardsForRandom();
        result.setKey(o);
        List<String> centerStringList = Arrays.asList(cards.getC_center().split(","));
        List<Integer> center = centerStringList.stream().map(Integer::valueOf).collect(Collectors.toList());
        result.setCardId(cards.getId()).

                setCenter(center);

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