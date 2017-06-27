package com.xt.landlords.game.mission.phase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xt.yde.thrift.card.mission.MissionCards;
import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class MissionPlayPhaseData extends PhaseData {


    public List<MissionPlayPhaseDataItem> getItems() {
        return items;
    }

    public MissionPlayPhaseData setItems(List<MissionPlayPhaseDataItem> items) {
        this.items = items;
        return this;
    }

    private List<MissionPlayPhaseDataItem> items = new ArrayList<>();

    public MissionPlayPhaseData addDealItem(MissionCards missionCards) {
        MissionPlayPhaseDealDataItem missionPlayPhaseDataItem = new MissionPlayPhaseDealDataItem();
        missionPlayPhaseDataItem.setCenterCards(missionCards.getCenter())
                .setRightCards(missionCards.getRight())
                .setLeftCards(missionCards.getLeft())
                .setUnderCards(missionCards.getUnder());
        //missionPlayPhaseDataItem.setOrderBy(items.size() + 1);
        items.add(missionPlayPhaseDataItem);
        return this;
    }

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
    public MissionPlayPhaseData addPlayDataItem(MissionPlayPhasePlayDataItem item) {
//        MissionPlayPhasePlayDataItem missionPlayPhaseDataItem = new MissionPlayPhasePlayDataItem();
        item.setOrderBy(items.size());
        items.add(item);
        return this;
    }


    private List<Integer> playCards;


    public List<Integer> getLastCards() {
        return lastCards;
    }

    public MissionPlayPhaseData setLastCards(List<Integer> lastCards) {
        this.lastCards = lastCards;
        return this;
    }

    public Integer getLastPlace() {
        return lastPlace;
    }

    public MissionPlayPhaseData setLastPlace(Integer lastPlace) {
        this.lastPlace = lastPlace;
        return this;
    }

    public Integer getNowPlace() {
        return nowPlace;
    }

    public MissionPlayPhaseData setNowPlace(Integer nowPlace) {
        this.nowPlace = nowPlace;
        return this;
    }

    public Integer getNextPosition() {
        return nextPosition;
    }

    public MissionPlayPhaseData setNextPosition(Integer nextPosition) {
        this.nextPosition = nextPosition;
        return this;
    }

    public Integer getCurrentBombNumbers() {
        return currentBombNumbers;
    }

    public MissionPlayPhaseData setCurrentBombNumbers(Integer currentBombNumbers) {
        this.currentBombNumbers = currentBombNumbers;
        return this;
    }

    public boolean isIfEnd() {
        return ifEnd;
    }

    public MissionPlayPhaseData setIfEnd(boolean ifEnd) {
        this.ifEnd = ifEnd;
        return this;
    }

    public Integer getTotalBombNumber() {
        return totalBombNumber;
    }

    public MissionPlayPhaseData setTotalBombNumber(Integer totalBombNumber) {
        this.totalBombNumber = totalBombNumber;
        return this;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public MissionPlayPhaseData setOrderBy(int orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    private int orderBy;


    @JsonIgnore()
    public MissionPlayPhasePlayDataItem getLastPlayDataItem() {
        MissionPlayPhasePlayDataItem missionPlayPhaseDataItem = (MissionPlayPhasePlayDataItem) items.stream().filter(p -> p instanceof
                MissionPlayPhasePlayDataItem).max(Comparator.comparing(MissionPlayPhaseDataItem::getOrderBy)).orElse
                (null);
        return missionPlayPhaseDataItem;
    }

    public boolean isWin() {
        return isWin;
    }

    public MissionPlayPhaseData setWin(boolean win) {
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

    @JsonIgnore()
    public MissionPlayPhaseDealDataItem getDealDataItem() {
        return (MissionPlayPhaseDealDataItem) items.stream().filter(p -> p instanceof MissionPlayPhaseDealDataItem)
                .findFirst().orElse(null);
    }
}