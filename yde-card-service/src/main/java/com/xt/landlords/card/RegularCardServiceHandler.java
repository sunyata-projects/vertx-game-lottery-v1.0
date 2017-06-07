package com.xt.landlords.card;

import com.xt.landlords.card.model.RegularCard17;
import com.xt.landlords.card.model.RegularCard37;
import com.xt.landlords.card.store.CardRegularStore;
import com.xt.yde.thrift.regular.RegularCards;
import com.xt.yde.thrift.regular.RegularCardsService;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.trylogic.spring.boot.thrift.annotation.ThriftController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
//import ru.trylogic.spring.boot.thrift.annotation.*;
//import ru.*

/**
 * Created by aleksandr on 01.09.15.
 */
@Component
@ThriftController("/regular")
public class RegularCardServiceHandler implements RegularCardsService.Iface {

    @Autowired
    CardRegularStore cardRegularStore;

    public void initialize() throws IOException {

    }


    @Override
    public RegularCards getCards17() throws TException {
        RegularCard17 card17 = cardRegularStore.getCard17();
        RegularCards regularCards = new RegularCards();
        List<String> strings = Arrays.asList(card17.getC_center().split(","));
        List<Integer> collect = strings.stream().map(Integer::valueOf).collect(Collectors.toList());
        return regularCards.setCardId(card17.getId()).setCenter(collect);
    }

    @Override
    public RegularCards getCards37(int prizeLevel, String centerId) throws TException {
        RegularCard37 card37 = cardRegularStore.getCard37(prizeLevel, centerId);
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
        return regularCards;
    }


}