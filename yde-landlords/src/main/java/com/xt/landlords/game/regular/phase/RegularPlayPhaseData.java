package com.xt.landlords.game.regular.phase;

import org.sunyata.octopus.model.PhaseData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class RegularPlayPhaseData extends PhaseData {
    private List<RegularPlayPhaseDataItem> items = new ArrayList<>();

    public List<RegularPlayPhaseDataItem> getItems() {
        return items;
    }

    public RegularPlayPhaseData setItems(List<RegularPlayPhaseDataItem> items) {
        this.items = items;
        return this;
    }

    public List<Integer> getLastCards() {
        return lastCards;
    }

    public RegularPlayPhaseData setLastCards(List<Integer> lastCards) {
        this.lastCards = lastCards;
        return this;
    }

    public Integer getLastPlace() {
        return lastPlace;
    }

    public RegularPlayPhaseData setLastPlace(Integer lastPlace) {
        this.lastPlace = lastPlace;
        return this;
    }

    public Integer getNowPlace() {
        return nowPlace;
    }

    public RegularPlayPhaseData setNowPlace(Integer nowPlace) {
        this.nowPlace = nowPlace;
        return this;
    }

    public Integer getNextPosition() {
        return nextPosition;
    }

    public RegularPlayPhaseData setNextPosition(Integer nextPosition) {
        this.nextPosition = nextPosition;
        return this;
    }

    public Integer getCurrentBombNumbers() {
        return currentBombNumbers;
    }

    public RegularPlayPhaseData setCurrentBombNumbers(Integer currentBombNumbers) {
        this.currentBombNumbers = currentBombNumbers;
        return this;
    }

    public boolean isIfEnd() {
        return ifEnd;
    }

    public RegularPlayPhaseData setIfEnd(boolean ifEnd) {
        this.ifEnd = ifEnd;
        return this;
    }

    public Integer getTotalBombNumber() {
        return totalBombNumber;
    }

    public RegularPlayPhaseData setTotalBombNumber(Integer totalBombNumber) {
        this.totalBombNumber = totalBombNumber;
        return this;
    }

    public void addPhaseDataItem(RegularPlayPhaseDataItem item) {
        item.setOrderBy(items.size() + 1);
        items.add(item);
    }

    public RegularPlayPhaseDataItem getLastDataItem() {
        RegularPlayPhaseDataItem regularPlayPhaseDataItem = items.stream().max(Comparator.comparing
                (RegularPlayPhaseDataItem::getOrderBy)).orElse(null);
        return regularPlayPhaseDataItem;
    }
    public boolean isWin() {
        return isWin;
    }

    public RegularPlayPhaseData setWin(boolean win) {
        isWin = win;
        return this;
    }


//    public void deleteItem(RegularPlayPhaseDataItem lastDataItem) {
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
}