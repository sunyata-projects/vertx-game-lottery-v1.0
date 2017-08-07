package com.xt.landlords.card;

import com.xt.landlords.card.model.PuzzleCard;
import com.xt.landlords.card.store.CardPuzzleStore;
import com.xt.yde.thrift.card.puzzle.PuzzleCards;
import com.xt.yde.thrift.card.puzzle.PuzzleCardsService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.sunyata.octopus.json.Json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Service
//@ThriftController("/puzzle")
public class PuzzleCardServiceHandler implements PuzzleCardsService.Iface {
    Logger logger = LoggerFactory.getLogger(EliminateCardServiceHandler.class);
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    CardPuzzleStore cardPuzzleStore;

    public List getWZ() {
        PuzzleCard wz = cardPuzzleStore.getWZ();
        return Json.decodeValue(wz.getCards(), List.class);
    }

    public List get22() {
        PuzzleCard wz = cardPuzzleStore.get22();
        return Json.decodeValue(wz.getCards(), List.class);
    }

    public List getNormal(int grade) {
        PuzzleCard wz = cardPuzzleStore.getNormal(grade);
        logger.info("拼图赛牌库id:{}", wz.getId());
        return Json.decodeValue(wz.getCards(), List.class);
    }

    Random random = new Random();

    public int nextInt(int from, int to) {
        int max = to;
        int min = from;
        int s = random.nextInt(max) % (max - min + 1) + min;
        return s;
    }

    public boolean is25Percent() {
        int i = nextInt(1, 4);
        return i == 1;
    }

    @Override
    public PuzzleCards getCards(int grade) throws TException {
        logger.info("拼图赛grade:{}", grade);
        List<List<List<Integer>>> result = new ArrayList<>();
        if (is25Percent()) {
            result.add(getWZ());
        }

        if (is25Percent()) {//25机率是用22来通过抽红包的方式来发奖
            result.add(get22());
        } else {
            result.add(getNormal(grade));
        }
        PuzzleCards cards = new PuzzleCards();
        cards.setCardId("cardId").setCards(result);
        logger.info("拼图赛获取牌库成功");
        return cards;
    }


    public void initialize() throws IOException {
    }
}