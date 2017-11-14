package com.xt.landlords.game.classic.phase;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by leo on 17/5/16.
 */
public class ClassicPlayPhasePlayDataItem extends ClassicPlayPhaseDataItem {

    public ClassicPlayPhasePlayDataItem() {
        this.createDateTime = new Timestamp(System.currentTimeMillis());
    }

    public List<Integer> getCenterCards() {
        return centerCards;
    }

    public ClassicPlayPhasePlayDataItem setCenterCards(List<Integer> centerCards) {
        this.centerCards = centerCards;
        return this;
    }

    public List<Integer> getRightCards() {
        return rightCards;
    }

    public ClassicPlayPhasePlayDataItem setRightCards(List<Integer> rightCards) {
        this.rightCards = rightCards;
        return this;
    }

    public List<Integer> getLeftCards() {
        return leftCards;
    }

    public ClassicPlayPhasePlayDataItem setLeftCards(List<Integer> leftCards) {
        this.leftCards = leftCards;
        return this;
    }

    public Integer getAiVersionFlag() {
        return aiVersionFlag;
    }

    public ClassicPlayPhasePlayDataItem setAiVersionFlag(Integer aiVersionFlag) {
        this.aiVersionFlag = aiVersionFlag;
        return this;
    }

    public Timestamp getCreateDateTime() {
        return createDateTime;
    }

    public ClassicPlayPhasePlayDataItem setCreateDateTime(Timestamp createDateTime) {
        this.createDateTime = createDateTime;
        return this;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public ClassicPlayPhasePlayDataItem setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    public boolean isAuto() {
        return isAuto;
    }

    public ClassicPlayPhasePlayDataItem setAuto(boolean auto) {
        isAuto = auto;
        return this;
    }

    public List<Integer> getShowCards() {
        return showCards;
    }

    public ClassicPlayPhasePlayDataItem setShowCards(List<Integer> showCards) {
        this.showCards = showCards;
        return this;
    }


    private List<Integer> centerCards;
    private List<Integer> rightCards;
    private List<Integer> leftCards;
    private Integer aiVersionFlag = 1;//AI版本标志,0－难版本（输），1－简单版本（赢）
    private boolean isAuto;//是否托管
    private List<Integer> showCards;//牌桌上的上一个玩家或上上玩家出的牌,需要你大过他的牌,有可能是自己出的牌
    private Integer orderBy;
    private Timestamp createDateTime;
}