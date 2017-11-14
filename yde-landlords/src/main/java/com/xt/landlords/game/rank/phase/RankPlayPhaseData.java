package com.xt.landlords.game.rank.phase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xt.yde.thrift.card.rank.RankCards;
import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class RankPlayPhaseData extends PhaseData {


    public List<RankPlayPhaseDataItem> getItems() {
        return items;
    }

    public RankPlayPhaseData setItems(List<RankPlayPhaseDataItem> items) {
        this.items = items;
        return this;
    }

    private List<RankPlayPhaseDataItem> items = new ArrayList<>();

    public RankPlayPhaseData addDealItem(RankCards rankCards) {
        RankPlayPhaseDealDataItem rankPlayPhaseDataItem = new RankPlayPhaseDealDataItem();
        rankPlayPhaseDataItem.setCenterCards(rankCards.getCenter())
                .setRightCards(rankCards.getRight())
                .setLeftCards(rankCards.getLeft())
                .setUnderCards(rankCards.getUnder());
        //missionPlayPhaseDataItem.setOrderBy(items.size() + 1);
        items.add(rankPlayPhaseDataItem);
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
    public RankPlayPhaseData addPlayDataItem(RankPlayPhasePlayDataItem item) {
//        MissionPlayPhasePlayDataItem missionPlayPhaseDataItem = new MissionPlayPhasePlayDataItem();
        item.setOrderBy(items.size());
        items.add(item);
        return this;
    }


    private List<Integer> playCards;


    public List<Integer> getLastCards() {
        return lastCards;
    }

    public RankPlayPhaseData setLastCards(List<Integer> lastCards) {
        this.lastCards = lastCards;
        return this;
    }

    public Integer getLastPlace() {
        return lastPlace;
    }

    public RankPlayPhaseData setLastPlace(Integer lastPlace) {
        this.lastPlace = lastPlace;
        return this;
    }

    public Integer getNowPlace() {
        return nowPlace;
    }

    public RankPlayPhaseData setNowPlace(Integer nowPlace) {
        this.nowPlace = nowPlace;
        return this;
    }

    public Integer getNextPosition() {
        return nextPosition;
    }

    public RankPlayPhaseData setNextPosition(Integer nextPosition) {
        this.nextPosition = nextPosition;
        return this;
    }

    public Integer getCurrentBombNumbers() {
        return currentBombNumbers;
    }

    public RankPlayPhaseData setCurrentBombNumbers(Integer currentBombNumbers) {
        this.currentBombNumbers = currentBombNumbers;
        return this;
    }

    public boolean isIfEnd() {
        return ifEnd;
    }

    public RankPlayPhaseData setIfEnd(boolean ifEnd) {
        this.ifEnd = ifEnd;
        return this;
    }

    public Integer getTotalBombNumber() {
        return totalBombNumber;
    }

    public RankPlayPhaseData setTotalBombNumber(Integer totalBombNumber) {
        this.totalBombNumber = totalBombNumber;
        return this;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public RankPlayPhaseData setOrderBy(int orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    private int orderBy;


    @JsonIgnore()
    public RankPlayPhasePlayDataItem getLastPlayDataItem() {
        RankPlayPhasePlayDataItem missionPlayPhaseDataItem = (RankPlayPhasePlayDataItem) items.stream().filter(p -> p
                instanceof
                RankPlayPhasePlayDataItem).max(Comparator.comparing(RankPlayPhaseDataItem::getOrderBy)).orElse
                (null);
        return missionPlayPhaseDataItem;
    }

    public boolean isWin() {
        return isWin;
    }

    public RankPlayPhaseData setWin(boolean win) {
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
    public RankPlayPhaseDealDataItem getDealDataItem() {
        return (RankPlayPhaseDealDataItem) items.stream().filter(p -> {
            return p instanceof RankPlayPhaseDealDataItem;
        })
                .findFirst().orElse(null);
    }
}