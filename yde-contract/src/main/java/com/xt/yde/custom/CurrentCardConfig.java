package com.xt.yde.custom;

import com.xt.yde.GameTypes;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Created by leo on 17/8/29.
 */
@Component
public class CurrentCardConfig {
    public List<CardConfigInfo> getItems() {
        return items;
    }

    public CurrentCardConfig setItems(List<CardConfigInfo> items) {
        this.items = items;
        return this;
    }

    private List<CardConfigInfo> items;

    public List<PrizeLevelConfigInfo> getPrizeLevelitems() {
        return prizeLevelitems;
    }

    public CurrentCardConfig setPrizeLevelitems(List<PrizeLevelConfigInfo> prizeLevelitems) {
        this.prizeLevelitems = prizeLevelitems;
        return this;
    }

    private List<PrizeLevelConfigInfo> prizeLevelitems;

    public boolean isOpen() {
        return isOpen;
    }

    public CurrentCardConfig setOpen(boolean open) {
        isOpen = open;
        return this;
    }

    private boolean isOpen;

    public CurrentCardConfig() {
        prizeLevelitems = new ArrayList<>();
        items = new ArrayList<>();

        isOpen = false;
        if (!isOpen) {
            return;
        }
        prizeLevelitems.add(new PrizeLevelConfigInfo().setGameType(GameTypes.Regular.getValue()).setPrizeRandom(5));
        //1-8
        prizeLevelitems.add(new PrizeLevelConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(5));

        prizeLevelitems.add(new PrizeLevelConfigInfo().setGameType(GameTypes.Crazy.getValue()).setPrizeRandom(1));

        prizeLevelitems.add(new PrizeLevelConfigInfo().setGameType(GameTypes.Classic.getValue()).setPrizeRandom(3));

        prizeLevelitems.add(new PrizeLevelConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(8));


        items.add(new CardConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(1).setCardIds(Arrays
                .asList("1027", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(2).setCardIds(Arrays
                .asList("999", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(3).setCardIds(Arrays
                .asList("865", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(4).setCardIds(Arrays
                .asList("809", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(5).setCardIds(Arrays
                .asList("671", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(6).setCardIds(Arrays
                .asList("555", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(7).setCardIds(Arrays
                .asList("444", "")));
        items.add(new CardConfigInfo().setGameType(GameTypes.Rank.getValue()).setPrizeRandom(8).setCardIds(Arrays
                .asList("330", "")));


        items.add(new CardConfigInfo().setGameType(GameTypes.Classic.getValue()).setPrizeRandom(1).setCardIds(Arrays
                .asList("1027", "")));
        items.add(new CardConfigInfo().setGameType(GameTypes.Classic.getValue()).setPrizeRandom(2).setCardIds(Arrays
                .asList("865", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Classic.getValue()).setPrizeRandom(3).setCardIds(Arrays
                .asList("809", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Classic.getValue()).setPrizeRandom(4).setCardIds(Arrays
                .asList("555", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Classic.getValue()).setPrizeRandom(5).setCardIds(Arrays
                .asList("330", "")));

        items.add(new CardConfigInfo().setGameType(GameTypes.Classic.getValue()).setPrizeRandom(6).setCardIds(Arrays
                .asList("", "")));//至尊


        items.add(new CardConfigInfo().setGameType(GameTypes.Regular.getValue()).setPrizeRandom(1).setCardIds(Arrays
                .asList("1960", "9799")));
        items.add(new CardConfigInfo().setGameType(GameTypes.Regular.getValue()).setPrizeRandom(2).setCardIds(Arrays
                .asList("1960", "9798")));
        items.add(new CardConfigInfo().setGameType(GameTypes.Regular.getValue()).setPrizeRandom(3).setCardIds(Arrays
                .asList("1960", "9797")));
        items.add(new CardConfigInfo().setGameType(GameTypes.Regular.getValue()).setPrizeRandom(4).setCardIds(Arrays
                .asList("1960", "9796")));
        items.add(new CardConfigInfo().setGameType(GameTypes.Regular.getValue()).setPrizeRandom(5).setCardIds(Arrays
                .asList("1960", "9796")));
        items.add(new CardConfigInfo().setGameType(GameTypes.Regular.getValue()).setPrizeRandom(6).setCardIds(Arrays
                .asList("1960", "9800")));


        //1.5 两关
        items.add(new CardConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(1).setCardIds(Arrays
                .asList("22645", "22646", "23", "23", "23")));
        //3 三关
        items.add(new CardConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(2).setCardIds(Arrays
                .asList("22645", "22646", "22647", "23", "23")));
        //6 四关
        items.add(new CardConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(3).setCardIds(Arrays
                .asList("22645", "22646", "22647", "22648", "23")));
        //50 五关
        items.add(new CardConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(4).setCardIds(Arrays
                .asList("22645", "22646", "22647", "22648", "22649")));


        //100  五关
        items.add(new CardConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(5).setCardIds(Arrays
                .asList("22645", "22646", "22647", "22648", "22649", "22650")));
        //200  五关
        items.add(new CardConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(6).setCardIds(Arrays
                .asList("22645", "22646", "22647", "22648", "22649", "22650")));
        //1000  五关
        items.add(new CardConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(7).setCardIds(Arrays
                .asList("22645", "22646", "22647", "22648", "22649", "22650")));
        //1000 五关
        items.add(new CardConfigInfo().setGameType(GameTypes.Mission.getValue()).setPrizeRandom(8).setCardIds(Arrays
                .asList("22645", "22646", "22647", "22648", "22649", "22650")));
    }

    public CardConfigInfo getCardConfigInfo(int gameType) {
        if (isOpen()) {
            PrizeLevelConfigInfo prizeLevelConfigInfo = getPrizeLevelConfigInfo(gameType);
            if (prizeLevelConfigInfo != null) {
                CardConfigInfo cardConfigInfo = items.stream().filter(p -> p.getGameType() == gameType && Objects
                        .equals(p

                                .getPrizeRandom(), prizeLevelConfigInfo.getPrizeRandom())).findFirst().orElse(null);
                return cardConfigInfo;
            }
        }
        return null;
    }

    public PrizeLevelConfigInfo getPrizeLevelConfigInfo(int gameType) {
        if (isOpen()) {
            PrizeLevelConfigInfo prizeLevelConfigInfo = prizeLevelitems.stream().filter(p -> p.getGameType() ==
                    gameType)

                    .findFirst().orElse(null);
            return prizeLevelConfigInfo;
        }
        return null;
    }
}
