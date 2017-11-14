package com.xt.landlords.game.classic.phase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class ClassicPlayPhaseData extends PhaseData {


    public List<ClassicPlayPhaseDataItem> getItems() {
        return items;
    }

    public ClassicPlayPhaseData setItems(List<ClassicPlayPhaseDataItem> items) {
        this.items = items;
        return this;
    }

    private List<ClassicPlayPhaseDataItem> items = new ArrayList<>();

//    public ClassicPlayPhaseData addDealItem(ClassicCards ClassicCards) {
//        ClassicPlayPhaseDealDataItem ClassicPlayPhaseDataItem = new ClassicPlayPhaseDealDataItem();
//        ClassicPlayPhaseDataItem.setCenterCards(ClassicCards.getCenter())
//                .setRightCards(ClassicCards.getRight())
//                .setLeftCards(ClassicCards.getLeft())
//                .setUnderCards(ClassicCards.getUnder());
//        //missionPlayPhaseDataItem.setOrderBy(items.size() + 1);
//        items.add(ClassicPlayPhaseDataItem);
//        return this;
//    }
//
//    public MissionPlayPhaseDataItem getLastPlayPhaseDataItem() {
//        MissionPlayPhaseDataItem missionPlayPhaseDataItem = items.stream().max(Comparator.comparing
//                (MissionPlayPhasePlayDataItem::getOrderBy)).orElse(null);
//        return missionPlayPhaseDataItem;
//    }

    //    public MissionPlayPhaseDataItem getPenultimateDataItem() {
//        MissionPlayPhaseDataItem lastPlayPhaseDataItem = getLastPlayPhaseDataItem();
//        if (lastPlayPhaseDataItem != null) {
//            int orderBy = lastPlayPhaseDataItem.getOrderBy() - 1;
//            MissionPlayPhaseDataItem MissionPlayPhaseDataItem = items.stream().filter(p -> p.getOrderBy() ==
//                    orderBy)
//                    .findFirst().orElse(null);
//            return MissionPlayPhaseDataItem;
//        }
//        return null;
//    }
    @JsonIgnore()
    public ClassicPlayPhaseData addPlayDataItem(ClassicPlayPhasePlayDataItem item) {
//        MissionPlayPhasePlayDataItem missionPlayPhaseDataItem = new MissionPlayPhasePlayDataItem();
        item.setOrderBy(items.size());
        items.add(item);
        return this;
    }


    private List<Integer> playCards;


    public List<Integer> getLastCards() {
        return lastCards;
    }

    public ClassicPlayPhaseData setLastCards(List<Integer> lastCards) {
        this.lastCards = lastCards;
        return this;
    }

    public Integer getLastPlace() {
        return lastPlace;
    }

    public ClassicPlayPhaseData setLastPlace(Integer lastPlace) {
        this.lastPlace = lastPlace;
        return this;
    }

    public Integer getNowPlace() {
        return nowPlace;
    }

    public ClassicPlayPhaseData setNowPlace(Integer nowPlace) {
        this.nowPlace = nowPlace;
        return this;
    }

    public Integer getNextPosition() {
        return nextPosition;
    }

    public ClassicPlayPhaseData setNextPosition(Integer nextPosition) {
        this.nextPosition = nextPosition;
        return this;
    }

    public Integer getCurrentBombNumbers() {
        return currentBombNumbers;
    }

    public ClassicPlayPhaseData setCurrentBombNumbers(Integer currentBombNumbers) {
        this.currentBombNumbers = currentBombNumbers;
        return this;
    }

    public boolean isIfEnd() {
        return ifEnd;
    }

    public ClassicPlayPhaseData setIfEnd(boolean ifEnd) {
        this.ifEnd = ifEnd;
        return this;
    }

    public Integer getTotalBombNumber() {
        return totalBombNumber;
    }

    public ClassicPlayPhaseData setTotalBombNumber(Integer totalBombNumber) {
        this.totalBombNumber = totalBombNumber;
        return this;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public ClassicPlayPhaseData setOrderBy(int orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    private int orderBy;


    @JsonIgnore()
    public ClassicPlayPhasePlayDataItem getLastPlayDataItem() {
        ClassicPlayPhasePlayDataItem missionPlayPhaseDataItem = (ClassicPlayPhasePlayDataItem) items.stream().filter(p -> p
                instanceof
                ClassicPlayPhasePlayDataItem).max(Comparator.comparing(ClassicPlayPhaseDataItem::getOrderBy)).orElse
                (null);
        return missionPlayPhaseDataItem;
    }

    public boolean isWin() {
        return isWin;
    }

    public ClassicPlayPhaseData setWin(boolean win) {
        isWin = win;
        return this;
    }


//    public void deleteItem(MissionPlayPhaseDataItem lastDataItem) {
//        items.remove(lastDataItem);
//    }


    private List<Integer> lastCards;//上一个出牌位置所出的牌
    private Integer lastPlace = 1;//上一个出牌人位置
    private Integer nowPlace = 1;//当前出牌人位置
    private Integer nextPosition = 1;//下一个位置
    private Integer currentBombNumbers = 0;//当前炸弹数量
    private boolean ifEnd = false;//是否结束
    private Integer totalBombNumber = 0;//总炸弹数量


    private boolean isWin;

    public void setPlayCards(List<Integer> playCards) {
        this.playCards = playCards;
    }

    public List<Integer> getPlayCards() {
        return playCards;
    }


    public ClassicPlayPhaseDataItem getLastDataItem() {
        ClassicPlayPhaseDataItem classicPlayPhaseDataItem = items.stream().max(Comparator.comparing
                (ClassicPlayPhaseDataItem::getOrderBy)).orElse(null);
        return classicPlayPhaseDataItem;
    }
    public void addPhaseDataItem(ClassicPlayPhaseDataItem item) {
        item.setOrderBy(items.size() + 1);
        items.add(item);
    }


//    @JsonIgnore()
//    public ClassicPlayPhaseDealDataItem getDealDataItem() {
//        return (ClassicPlayPhaseDealDataItem) items.stream().filter(p -> {
//            return p instanceof ClassicPlayPhaseDealDataItem;
//        })
//                .findFirst().orElse(null);
//    }
}