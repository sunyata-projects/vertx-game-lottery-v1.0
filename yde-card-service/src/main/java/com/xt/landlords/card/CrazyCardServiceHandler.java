package com.xt.landlords.card;

import com.xt.landlords.card.model.CrazyCard;
import com.xt.landlords.card.store.CardCrazyStore;
import com.xt.yde.thrift.card.crazy.CrazyCards;
import com.xt.yde.thrift.card.crazy.CrazyCardsService;
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
 * Created by;/ aleksandr on 01.09.15.
 */
@Service
public class CrazyCardServiceHandler implements CrazyCardsService.Iface {

    Logger logger = LoggerFactory.getLogger(EliminateCardServiceHandler.class);
    @Autowired
    CardCrazyStore cardCrazyStore;

    public void initialize() throws IOException {

    }


    @Override
    public CrazyCards getCards(int prizeLevel) throws TException {
        CrazyCard card = null;
//
//        crazyMap.put(0, "0");
//        crazyMap.put(1, "500");8
//        crazyMap.put(2, "50");7
//        crazyMap.put(3, "10");6
//        crazyMap.put(4, "4");5
//        crazyMap.put(5, "3");4
//        crazyMap.put(6, "2");3
//        crazyMap.put(7, "1.5");2

        if (prizeLevel != 8) {
            prizeLevel = 9 - prizeLevel;
            if (prizeLevel == 9) {
                prizeLevel = 0;
            }
            card = cardCrazyStore.getCards(prizeLevel);
            //card  = cardCrazyStore.getCardsById("43852");//// TODO: 17/10/17 测试使用,用后改回来
        } else {//至尊牌型
            card = new CrazyCard();
            card
                    .setId("-1")
                    .setBomb_nums(0)
                    .setC_center("53,52,51,38,25,12,50,37,24,49,48,47,46,45,44,43,42")
                    .setC_center_three("41,40,39")
                    .setC_left("0,1,2,3,4,5,6,7,8,9,10,11,13,14,15,16")
                    .setC_left_one("17")
                    .setC_right("18,19,20,21,22,23,26,27,28,29,30,31,32,33,34,35")
                    .setC_right_one("36");

            //String s = "53,52,51,38,25,12,50,37,24,49,48,47,46,45,44,43,42,41,40,39";
        }
//        card = cardCrazyStore.getCardsById("56282");
        CrazyCards cardThrift = new CrazyCards();
        List<String> right = Arrays.asList(card.getC_right().split(","));
        List<Integer> rightList = right.stream().map(Integer::valueOf).collect(Collectors.toList());
        Integer rightOne = Integer.valueOf(card.getC_right_one());

        List<String> left = Arrays.asList(card.getC_left().split(","));
        List<Integer> leftList = left.stream().map(Integer::valueOf).collect(Collectors.toList());
        Integer leftOne = Integer.valueOf(card.getC_left_one());

        List<String> center = Arrays.asList(card.getC_center().split(","));
        List<Integer> centerList = center.stream().map(Integer::valueOf).collect(Collectors.toList());

        List<String> centerThree = Arrays.asList(card.getC_center_three().split(","));
        List<Integer> centerThreeList = centerThree.stream().map(Integer::valueOf).collect(Collectors.toList());

        cardThrift.setCardId(card.getId())
                .setCenter(centerList).setCenterThree(centerThreeList)
                .setRight(rightList).setRightOne(rightOne)
                .setLeft(leftList).setLeftOne(leftOne)
                .setBombNums(card.getBomb_nums());
        return cardThrift;
    }
}